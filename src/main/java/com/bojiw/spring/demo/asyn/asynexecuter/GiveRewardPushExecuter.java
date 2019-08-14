
package com.bojiw.spring.demo.asyn.asynexecuter;

import com.asyncmd.model.AbstractAsynExecuter;
import com.asyncmd.model.AsynExecuterConf;
import com.bojiw.spring.demo.asyn.asynbiz.GiveRewardBiz;
import com.bojiw.spring.demo.asyn.asyncmd.GiveRewardAsynCmd;
import org.springframework.stereotype.Service;

/**
 * push通知处理器
 * @author wangwendi
 * @version $Id: GiveRewardPushExecuter.java, v 0.1 2019年07月23日 下午11:07 wangwendi Exp $
 */
//只有赠送成功才会进行通知 所以设置push通知处理器最后一个执行
@AsynExecuterConf(sort = 90)
@Service
public class GiveRewardPushExecuter extends AbstractAsynExecuter<GiveRewardAsynCmd> {

    @Override
    protected void executer(GiveRewardAsynCmd cmd) {
        GiveRewardBiz giveRewardBiz = cmd.getContent();
        if (giveRewardBiz.getGold() != null){
            System.out.println("赠送金币成功push通知");
        }
        if (giveRewardBiz.getIntegral() != null){
            System.out.println("赠送积分成功push通知");
        }
    }
}