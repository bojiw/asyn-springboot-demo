
package com.bojiw.spring.demo.asyn.asynbiz;

import com.asyncmd.model.AsynBizObject;

/**
 * 短信业务模型
 * @author wangwendi
 * @version $Id: SmsBiz.java, v 0.1 2019年07月23日 下午8:58 wangwendi Exp $
 */
public class SmsBiz extends AsynBizObject {
    /**
     * 手机号
     */
    private String mobiles;
    /**
     * 短信内容
     */
    private String content;

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}