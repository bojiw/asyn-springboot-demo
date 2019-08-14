package com.bojiw.spring.demo.mq.consumer;

import com.bojiw.spring.demo.asyn.asynbiz.OrderCreateBiz;
import com.bojiw.spring.demo.asyn.asynbiz.OrderModifyBiz;
import com.bojiw.spring.demo.asyn.asyncmd.OrderCreateAsynCmd;
import com.bojiw.spring.demo.asyn.asyncmd.OrderModifyAsynCmd;
import com.bojiw.spring.demo.server.AsynCmdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单修改消息
 * @author wangwendi
 * @date 2019/8/14
 */
@Service
public class OrderModifyListener {

    @Autowired
    private AsynCmdServer asynCmdServer;

    public Integer consumer(String orderId){
        OrderModifyBiz orderModifyBiz = new OrderModifyBiz();
        orderModifyBiz.setOrderId(orderId);
        String bizId = OrderModifyAsynCmd.type + orderId;
        // 重点这里的relyBizId需要和订单创建消息的bizId一样 这样才能保证同一笔订单 肯定是先执行订单创建消息 再处理订单修改消息
        String relyBizId = OrderCreateAsynCmd.type + orderId;
        asynCmdServer.notify(orderModifyBiz,bizId,OrderModifyAsynCmd.class,relyBizId);
        return 1;
    }
}