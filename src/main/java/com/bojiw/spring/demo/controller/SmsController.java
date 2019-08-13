
package com.bojiw.spring.demo.controller;

import com.afa.asyncmdboot.asyn.SmsAsynCmd;
import com.afa.asyncmdboot.asyn.asynbiz.SmsBiz;
import com.afa.asyncmdboot.service.AsynCmdServer;
import com.asyncmd.utils.SnowflakeIdWorkerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送短信接口
 * @author wangwendi
 * @version $Id: SmsController.java, v 0.1 2019年07月23日 下午8:55 wangwendi Exp $
 */
@RestController
@RequestMapping("/asyn")
public class SmsController {


    @Autowired
    private AsynCmdServer asynCmdServer;



    /**
     * http://localhost:8080/asyn/sendsms?mobiles=130120122,123121331&content=1111&requestId=123123123
     * @param mobiles
     * @param content
     * @return
     */
    @GetMapping(value = "/sendsms")
    public String sendsms(String mobiles, String content) {
        //效验手机号
        //效验手机内容
        SmsBiz smsBiz = new SmsBiz();
        smsBiz.setContent(content);
        smsBiz.setMobiles(mobiles);
        //唯一业务id 全局唯一 使用雪花算法生成唯一id
        SnowflakeIdWorkerUtil snowflakeIdWorkerUtil = new SnowflakeIdWorkerUtil(0,0);

        String bizId = SmsAsynCmd.name + mobiles + snowflakeIdWorkerUtil.nextId();
        asynCmdServer.notify(smsBiz,bizId,SmsAsynCmd.class);

        return "ok";
    }

}