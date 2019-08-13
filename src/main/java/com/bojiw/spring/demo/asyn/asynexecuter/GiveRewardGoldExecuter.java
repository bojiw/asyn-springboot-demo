
package com.bojiw.spring.demo.asyn.asynexecuter;

import com.asyncmd.model.AbstractAsynExecuter;
import com.asyncmdDemo.asyn.asynbiz.GiveRewardBiz;
import com.asyncmdDemo.asyn.asyncmd.GiveRewardAsynCmd;
import org.springframework.stereotype.Service;

/**
 * 赠送金币执行器
 * @author wangwendi
 * @version $Id: GiveRewardGoldExecuter.java, v 0.1 2019年07月23日 下午10:09 wangwendi Exp $
 */
@Service
public class GiveRewardGoldExecuter extends AbstractAsynExecuter<GiveRewardAsynCmd> {
    @Override
    protected void executer(GiveRewardAsynCmd cmd) {
        GiveRewardBiz giveRewardBiz = cmd.getContent();
        if (giveRewardBiz.getGold() == null){
            return;
        }
        System.out.println("赠送金币逻辑");
        System.out.println("赠送" + giveRewardBiz.getGold() + "个金币");
    }
}