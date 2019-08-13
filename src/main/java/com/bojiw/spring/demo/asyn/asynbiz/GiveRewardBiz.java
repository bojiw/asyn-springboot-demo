
package com.bojiw.spring.demo.asyn.asynbiz;

import com.asyncmd.model.AsynBizObject;

/**
 * 赠送奖励业务模型
 * @author wangwendi
 * @version $Id: GiveRewardBiz.java, v 0.1 2019年07月23日 下午9:56 wangwendi Exp $
 */
public class GiveRewardBiz extends AsynBizObject {

    /**
     * 积分
     */
    private Integer integral;
    /**
     * 金币
     */
    private Integer gold;

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}