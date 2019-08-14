
package com.bojiw.spring.demo.server.impl;

import com.asyncmd.manager.AsynExecuterFacade;
import com.asyncmd.model.AsynBizObject;
import com.asyncmd.model.AsynCmd;
import com.bojiw.spring.demo.server.AsynCmdServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangwendi
 * @version $Id: AsynCmdServerImpl.java, v 0.1 2019年07月23日 下午9:06 wangwendi Exp $
 */
@Service
public class AsynCmdServerImpl implements AsynCmdServer {

    @Autowired
    private AsynExecuterFacade asynExecuterFacade;


    @Override
    public void notify(AsynBizObject asynBizObject,String bizId,Class<? extends AsynCmd> asynCmdClass,String relyBizId) {
        try {
            AsynCmd asynCmd = asynCmdClass.newInstance();
            asynCmd.setContent(asynBizObject);
            asynCmd.setBizId(bizId);
            asynCmd.setRelyBizId(relyBizId);
            asynExecuterFacade.saveExecuterAsynCmd(asynCmd);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}