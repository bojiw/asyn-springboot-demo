package com.bojiw.spring.demo.mq.consumer;

import com.bojiw.spring.demo.asyn.asynbiz.OrderCreateBiz;
import com.bojiw.spring.demo.asyn.asyncmd.OrderCreateAsynCmd;
import com.bojiw.spring.demo.server.AsynCmdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单创建消息
 * @author wangwendi
 * @date 2019/8/14
 */
@Service
public class OrderCreateListener {

    @Autowired
    private AsynCmdServer asynCmdServer;

    public Integer consumer(String orderId){
        OrderCreateBiz orderCreateBiz = new OrderCreateBiz();
        orderCreateBiz.setOrderId(orderId);
        String bizId = OrderCreateAsynCmd.type + orderId;
        asynCmdServer.notify(orderCreateBiz,bizId,OrderCreateAsynCmd.class,null);
        return 1;
    }
}