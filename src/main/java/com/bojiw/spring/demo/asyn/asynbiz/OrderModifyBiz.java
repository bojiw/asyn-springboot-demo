package com.bojiw.spring.demo.asyn.asynbiz;

import com.asyncmd.model.AsynBizObject;

/**
 * @author wangwendi
 * @date 2019/8/14
 */
public class OrderModifyBiz extends AsynBizObject {

    /**
     * 订单id
     */
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}