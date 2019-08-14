package com.bojiw.spring.demo.controller;

import com.asyncmd.utils.SnowflakeIdWorkerUtil;
import com.bojiw.spring.demo.asyn.asynbiz.SmsBiz;
import com.bojiw.spring.demo.asyn.asyncmd.SmsAsynCmd;
import com.bojiw.spring.demo.rabbitmq.SsoMsgExchangeNameEnum;
import com.bojiw.spring.demo.rabbitmq.producer.SendMqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangwendi
 * @date 2019/8/13
 */
@RestController
@RequestMapping("/mq")
public class MqController {

    @Autowired
    private SendMqMessage sendMqMessage;

    @GetMapping(value = "/send")
    public String sendsms(String content) {

        sendMqMessage.sendMsg(content,SsoMsgExchangeNameEnum.SSO_TOPIC_EXCHANGE);
        return "ok";
    }
}