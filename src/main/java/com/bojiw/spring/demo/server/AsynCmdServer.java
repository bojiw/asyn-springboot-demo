
package com.bojiw.spring.demo.server;

import com.asyncmd.model.AsynBizObject;
import com.asyncmd.model.AsynCmd;

/**
 * @author wangwendi
 * @version $Id: AsynCmdServer.java, v 0.1 2019年07月23日 下午9:05 wangwendi Exp $
 */
public interface AsynCmdServer {

    /**
     * demo参考
     * @param asynBizObject
     * @param bizId
     * @param asynCmdClass
     */
    void notify(AsynBizObject asynBizObject, String bizId, Class<? extends AsynCmd> asynCmdClass, String relyBizId);
}