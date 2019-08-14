
package com.bojiw.spring.demo.asyn.asyncmd;

import com.asyncmd.enums.DispatchMode;
import com.asyncmd.model.AsynCmd;
import com.asyncmd.model.AsynCmdConf;
import com.bojiw.spring.demo.asyn.asynbiz.SmsBiz;

/**
 * 短信异步命令
 * @author wangwendi
 * @version $Id: SmsAsynCmd.java, v 0.1 2019年07月23日 下午8:57 wangwendi Exp $
 */
//如果有需要对某个异步命令对象做个性化设置 可以通过注解的方式 设置调度方式为调度中心调度 设置调度频率为 4s,4s,4m
@AsynCmdConf(dispatchMode = DispatchMode.DISPATCH,executerFrequency = "4s,4s,1m")
public class SmsAsynCmd extends AsynCmd<SmsBiz> {

    public static final String name = "sms";


    /**
     * 需要返回业务对象 组件里要用
     * @return
     */
    @Override
    protected Class getObject() {
        return SmsBiz.class;
    }
}