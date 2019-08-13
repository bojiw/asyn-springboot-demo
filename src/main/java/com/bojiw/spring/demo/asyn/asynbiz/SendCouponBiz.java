
package com.bojiw.spring.demo.asyn.asynbiz;

import com.asyncmd.model.AsynBizObject;

import java.util.List;

/**
 * 定向赠券业务模型
 * @author wangwendi
 * @version $Id: SendCouponBiz.java, v 0.1 2019年07月24日 下午5:35 wangwendi Exp $
 */
public class SendCouponBiz extends AsynBizObject {
    private List<Long> userIds;

    private List<Long> couponIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Long> couponIds) {
        this.couponIds = couponIds;
    }
}