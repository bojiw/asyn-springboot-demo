package com.bojiw.spring.demo.asyn.asynexecuter;

import com.asyncmd.model.AbstractAsynExecuter;
import com.bojiw.spring.demo.asyn.asynbiz.OrderCreateBiz;
import com.bojiw.spring.demo.asyn.asyncmd.OrderCreateAsynCmd;
import org.springframework.stereotype.Service;

/**
 * @author wangwendi
 * @date 2019/8/14
 */
@Service
public class OrderCreateExecuter extends AbstractAsynExecuter<OrderCreateAsynCmd> {
    @Override
    protected void executer(OrderCreateAsynCmd cmd) {
        OrderCreateBiz content = cmd.getContent();
        System.out.println("处理订单创建业务");
    }
}