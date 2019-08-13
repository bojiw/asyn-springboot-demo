
package com.bojiw.spring.demo.asyn.asynexecuter;

import com.asyncmd.model.AbstractAsynExecuter;
import com.asyncmdDemo.asyn.asynbiz.GiveRewardBiz;
import com.asyncmdDemo.asyn.asyncmd.GiveRewardAsynCmd;
import org.springframework.stereotype.Service;

/**
 * 赠送积分异步处理器
 * @author wangwendi
 * @version $Id: GiveRewardIntegralExecuter.java, v 0.1 2019年07月23日 下午9:56 wangwendi Exp $
 */
@Service
public class GiveRewardIntegralExecuter extends AbstractAsynExecuter<GiveRewardAsynCmd> {
    @Override
    protected void executer(GiveRewardAsynCmd cmd) {
        GiveRewardBiz giveRewardBiz = cmd.getContent();
        if (giveRewardBiz.getIntegral() == null){
            return;
        }
        System.out.println("开始赠送积分");
        System.out.println("赠送" + giveRewardBiz.getIntegral() + "积分");
    }
}