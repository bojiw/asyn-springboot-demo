package com.bojiw.spring.demo.controller;

import com.afa.asyncmdboot.asyn.SendCouponAsynCmd;
import com.afa.asyncmdboot.asyn.asynbiz.SendCouponBiz;
import com.asyncmd.manager.AsynExecuterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券定向赠券服务
 * @author wangwendi
 * @version $Id: CouponController.java, v 0.1 2019年07月24日 下午5:34 wangwendi Exp $
 */
@Controller
@RequestMapping("/asyn")
public class CouponController {

    @Autowired
    private AsynExecuterFacade asynExecuterFacade;

    /**
     * http://localhost:8080/asyn/sendCoupons?userIds=1111&coupons=1&requestId=312312312&sendDate=2019-07-2212:22:22
     * @param userIds
     * @param coupons
     * @param sendDate
     * @param requestId
     * @return
     */
    @RequestMapping(value = "/sendCoupons")
    @ResponseBody
    public String sendCoupons(String userIds, String coupons, String sendDate, String requestId) {
        SendCouponBiz sendCouponBiz = new SendCouponBiz();
        sendCouponBiz.setCouponIds(getList(coupons));
        sendCouponBiz.setUserIds(getList(userIds));
        SendCouponAsynCmd sendCouponAsynCmd = new SendCouponAsynCmd();
        sendCouponAsynCmd.setContent(sendCouponBiz);
        String bizId = SendCouponAsynCmd.type + requestId;
        sendCouponAsynCmd.setBizId(bizId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
        //设置发送时间 需要在SendCouponExecuter设置调度方式为调度中心调度 不然会立刻发送
        try {
            sendCouponAsynCmd.setNextTime(format.parse(sendDate));

        }catch (Exception e){
            throw new RuntimeException(e.getCause());
        }
        asynExecuterFacade.saveExecuterAsynCmd(sendCouponAsynCmd);
        return "ok";
    }

    private List<Long> getList(String value){
        if (StringUtils.isEmpty(value)){
            return new ArrayList<>();
        }
        List<Long> list = new ArrayList<>();
        String[] split = value.split(",");
        for (String userId : split){
            list.add(Long.valueOf(userId));
        }
        return list;
    }
}