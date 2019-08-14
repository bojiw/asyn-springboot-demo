
package com.bojiw.spring.demo.controller;

import com.bojiw.spring.demo.asyn.asynbiz.GiveRewardBiz;
import com.bojiw.spring.demo.asyn.asyncmd.GiveRewardAsynCmd;
import com.bojiw.spring.demo.server.AsynCmdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 赠送奖励
 * @author wangwendi
 * @version $Id: GiveRewardController.java, v 0.1 2019年07月23日 下午9:46 wangwendi Exp $
 */
@Controller
@RequestMapping("/asyn")
public class GiveRewardController {


    @Autowired
    private AsynCmdServer asynCmdServer;


    /**
     * http://localhost:8080/asyn/givereward?integral=11&gold=11&actId=1&userId=1000
     * @param integral
     * @param gold
     * @param actId
     * @param userId
     * @return
     */
    @RequestMapping(value = "/givereward")
    @ResponseBody
    public String sendsms(Integer integral,Integer gold,String actId,String userId) {
        GiveRewardBiz smsBiz = new GiveRewardBiz();
        smsBiz.setGold(gold);
        smsBiz.setIntegral(integral);
        //唯一业务id 根据业务来定义 需要全局唯一
        String bizId = "give" + userId + actId + System.currentTimeMillis();
        asynCmdServer.notify(smsBiz,bizId,GiveRewardAsynCmd.class,null);
        return "ok";
    }


}