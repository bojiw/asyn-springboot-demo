
package com.bojiw.spring.demo.asyn.asynexecuter;

import com.asyncmd.model.AbstractAsynExecuter;
import com.bojiw.spring.demo.asyn.asynbiz.SendCouponBiz;
import com.bojiw.spring.demo.asyn.asynbiz.SmsBiz;
import com.bojiw.spring.demo.asyn.asyncmd.SendCouponAsynCmd;
import com.bojiw.spring.demo.asyn.asyncmd.SmsAsynCmd;
import com.bojiw.spring.demo.server.AsynCmdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangwendi
 * @version $Id: SendCouponExecuter.java, v 0.1 2019年07月24日 下午5:37 wangwendi Exp $
 */
@Service
public class SendCouponExecuter extends AbstractAsynExecuter<SendCouponAsynCmd> {


    @Autowired
    private AsynCmdServer asynCmdServer;
    @Override
    protected void executer(SendCouponAsynCmd cmd) {
        SendCouponBiz content = cmd.getContent();
        System.out.println("赠送优惠券成功");
        //模拟逻辑 获取到手机号

        String mobils = "133212131,12312222";
        SmsBiz smsBiz = new SmsBiz();
        smsBiz.setMobiles(mobils);
        smsBiz.setContent("恭喜你成功收到优惠券");
        String bizId = "sms" + mobils + cmd.getBizId();
        asynCmdServer.notify(smsBiz,bizId,SmsAsynCmd.class,null);

    }
}