# asyn-springboot-demo

需要根据sql文件夹里的sql语句创建表
然后修改application.yml里的数据库url和用户名密码

三个测试功能
- SmsController 接收发送短信请求异步发送
- GiveRewardController 接收赠送奖励请求 异步赠送奖励
- CouponController 定时赠送指定用户一批优惠券
- OrderCreateListener和OrderModifyListener实现mq的顺序消费 保证同一笔订单肯定先处理订单创建消息 再处理订单修改消息

com.asyncmdDemo.asyn.asynbiz 存放异步命令业务对象
com.asyncmdDemo.asyn.asyncmd 存放异步命令对象
com.asyncmdDemo.asyn.asynexecuter 存放异步命令执行器
com.asyncmdDemo.asyn.callback 执行器执行异常回调

SmsAsynCmd异步命令对象里设置里一些异步命令个性化的设置

## 快速使用
1、引入jar

```
        <dependency>
            <groupId>com.bojiw</groupId>
            <artifactId>asyncmd-spring-boot-starter-core</artifactId>
            <version>1.0</version>
        </dependency>
```

2、核心配置项 必填
```
asyn:
  #core核心必填项
  core:
    #所在环境 会保证到表中 这样就可以解决预发环境的命令只会在预发环境上执行 做到不同环境之间哪怕用的数据库一样 也可以隔离
    env: test
    #定时任务名称 重点：需要不同工程不一样 推荐用应用名称来命名 如果多个项目定义的相同 定时任务会有问题
    jobName: test-boot
    #分表数量 正式环境一定要填写 设置需要是2的倍数如 4 8 16 32 64 因为分表位计算是通过biz_id 然后和hashmap相同的计算下标的方式 & hash
    #4代表分了4张异步命令表
    #也代表有4个线程同时捞取4张异步命令表的数据进行执行 测试环境可以不填写
    #默认一张异步命令表 后台只会有一个线程捞取数据
    tableNum: 4
    #zookeeper地址
    zookeeperUrl: 127.0.0.1:2181
    #重试执行频率 5s,10s,1m,1h 频率建议不要小于1秒
    #代表第一次重试5秒以后执行 第二次10秒以后执行 第三次1分钟以后执行 第四次1小时以后执行 之后都是间隔1小时执行
    #执行频率 5s,10s,20s
    #代表第一次重试5秒以后执行 第二次10秒以后执行 第三次20秒以后执行
    #这个参数如果想不同异步命令频率不一样 可以在异步命令执行器也有参数可以设置
    executerFrequencys: 4s,5s,6s

```
3、创建自己业务的asynBizObject,asynCmd,asynExecuter
主要是继承AsynBizObject，AsynCmd，AbstractAsynExecuter
例子：
```
/**
 * 短信业务模型
 * @author wangwendi
 * @version $Id: SmsBiz.java, v 0.1 2019年07月23日 下午8:58 wangwendi Exp $
 */
public class SmsBiz extends AsynBizObject {
    /**
     * 手机号
     */
    private String mobiles;
    /**
     * 短信内容
     */
    private String content;

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
```
```
/**
 * 短信异步命令
 * @author wangwendi
 * @version $Id: SmsAsynCmd.java, v 0.1 2019年07月23日 下午8:57 wangwendi Exp $
 */
public class SmsAsynCmd extends AsynCmd<SmsBiz> {

    /**
     * 必须实现 返回对应的AsynBizObject
    **/
    @Override
    protected Class getObject() {
        return SmsBiz.class;
    }
}
```
```
/**
 * 短信异步命令执行器 需要注入到spring容器中 加@Service注解或者xml配置bean
 * @author wangwendi
 * @version $Id: SmsExecuter.java, v 0.1 2019年07月23日 下午9:01 wangwendi Exp $
 */
@Service
public class SmsExecuter extends AbstractAsynExecuter<SmsAsynCmd> {

    /**
      * 具体业务逻辑 调度方式有三种 异步调度(保存异步命令以后，立刻扔到线程池执行)、调度中心调度(保存异步命令以后不做任何操作 由定时任务捞取数据进行执行)、同步调度(保存异步命令以后立刻同步执行executer方法) 默认是异步调度 可以设置其他三种方式调度 设置方法看demo或者详细配置项
    **/
    @Override
    protected void executer(SmsAsynCmd cmd) {
        SmsBiz content = cmd.getContent();
        System.out.println("发送短信");
        System.out.println("短信手机号" + content.getMobiles());
    }
}
```
4、执行源码sql目录下的asyn.sql创建表 
根据前面第三步设置的asynGroupConfig.tableNum分表数量来创建对应的表数量 如上面设置的4 则创建asyn_cmd00,asyn_cmd01,asyn_cmd02,asyn_cmd03 4张表

5、使用异步命令门面服务AsynExecuterFacade 保存异步命令 可以直接用@Autowired注解注入就可以

```
    @Autowired
    private AsynExecuterFacade asynExecuterFacade;
    //创建业务对象 创建异步命令对象 然后调用方法 就会自动执行SmsExecuter.executer方法
      SmsBiz smsBiz = new SmsBiz();
      smsBiz.setContent(content);
      smsBiz.setMobiles(mobiles);
      SmsAsynCmd asynCmd = new SmsAsynCmd();
      asynCmd.setContent(smsBiz);
      asynCmd.setBizId(bizId);
      asynExecuterFacade.saveExecuterAsynCmd(asynCmd);

```
## 注意点
- 为了保证可靠性 当出现极端情况下 比如执行器执行成功了 修改命令状态时系统挂了 会出现重复执行的情况 所以执行器需要保证幂等
- 如果应用系统已经使用了quartz 需要版本为2.1.7以上

## 分表情况根据业务id查询保存在哪张表
调用SubTableUtil#getIndex(分表数量,bizId) 可以获取异步命令在哪张表中 方便排查问题

## 详细配置项
```
  #执行线程池配置
  pool:
    #最小线程池 默认10
    corePoolSize: 20
    #最大线程数 默认150
    maxPoolSize: 200
    #缓存队列长度 默认300
    queueCapacity: 300
  #调度中心调度执行相关配置
  executerjob:
    #从数据库捞取未执行的异步命令多久捞取一次 默认是1秒捞取一次  0/2 * * * * ?代表2秒捞取一次
    cron: 0/2 * * * * ?
    #异步命令是否要先进后出 默认先进先出 true代表后进先出 注意这个只针对一张表的情况
    desc: true
    #一次从一张表中捞取命令数量 默认20条数据 10代表捞取10条数据
    limit: 20
    #重试次数 默认重试12次 20代表重试20次
    retryNum: 20
  #备份定时任务相关配置
  backupjob:
    #是否开启自动备份异步命令表的功能 开启需要在数据库中创建异步命令历史表 表的数量和异步命令表相同
    #默认每天凌晨2点钟开启定时任务把一个月以前的数据从异步命令表拷贝到异步命令历史表中 把异步命令表中备份成功的数据删除
    #最多每天同步每张表10w条数据 这样定时清理数据可以保证异步命令表的查询效率高
    #可以修改默认配置
    backup: true
    #配置什么时候执行定时任务 默认每天凌晨2点
    backupCron: 0/3 * * * * ?
    #配置备份多久以前的数据 20代表把20天以前的异步命令表数据都清理 备份到异步命令历史表中
    beforeDate: 3
    #配置备份任务一次最多每张表处理多少条数据 默认是10w条数据 10代表处理1w条数据
    maxNo: 20
  restjob:
    #重置状态任务执行频率 默认每隔60秒执行一次
    restCron: 0/30 * * * * ?

```


对不同异步命令对象进行个性化对配置
```
//如果有需要对某个异步命令对象做个性化设置 可以通过注解的方式 设置调度方式为调度中心调度 设置调度频率为 4s,4s,4m
@AsynCmdConf(dispatchMode = DispatchMode.DISPATCH,executerFrequency = "4s,4s")
public class SmsAsynCmd extends AsynCmd<SmsBiz> {

    public static final String name = "sms";
    /**
     * 需要返回业务对象类 组件里要用
     * @return
     */
    @Override
    protected Class getObject() {
        return SmsBiz.class;
    }
}
```

设置异步命令执行器的排序值 数值越大 越早执行 默认100
```
//设置排序值为90
@AsynExecuterConf(sort = 90)
@Service
public class SmsExecuter extends AbstractAsynExecuter<SmsAsynCmd> {

```



## 模型
asynCmdDB(异步命令对象)

|  字段名 |  描述 |
| :------------ | :------------ |
| cmdId  |  单表唯一id由表自增 |
| cmdType  | 异步命令类型 异步命令对象类的名称  |
| bizId  | 业务id 全局唯一 根据这个ID做hash然后取余获取分表位 计算逻辑和hashmap计算下标为相同  |
| content  | 业务上下文 业务对象AsynBizObject的json数据  |
| executeNum  | 异步命令对象执行次数 重试一次加1  |
| nextTime  | 下一次执行时间 根据设置的重试频率来计算  |
| status  | 状态 分为"INIT","初始化" "EXECUTE","执行中" "SUCCESS","成功" "ERROR","失败"  |
| createHostName  | 执行插入异步命令服务器的主机名 方便排查问  |
| createIp  | 执行插入异步命令服务器的ip地址 方便排查问题  |
| updateHostName  | 更新异步命令服务器的主机名 方便排查问题   |
| updateIp  | 更新异步命令服务器的ip地址 方便排查问题  |
| createName  | 创建异步命令的人 默认system   |
| successExecuters  | 执行成功的异步命令执行器类名 如果一个异步命令对象对应多个异步命令执行器 通过这个可以看有成功执行的异步命令对象   |
| env  | 所在环境  |
| exception  | 异步命令执行时的异常信息  |
| rely_biz_id  | 依赖的异步命令业务id  |
| gmtCreate  | 创建时间  |
| gmtModify  | 修改时间  |

AsynCmdHistoryDO异步命令历史模型 字段和内容和异步命令对象模型一样 只有创建时间和更新时间不同

## 三种调度方式
DispatchMode
异步调度
![image](http://qiniu.bojiw.com/20197/2019730202546image.png)
调度中心调度
![image](http://qiniu.bojiw.com/20197/2019730203025image.png)
同步调度
![image](http://qiniu.bojiw.com/20197/2019730203249image.png)





