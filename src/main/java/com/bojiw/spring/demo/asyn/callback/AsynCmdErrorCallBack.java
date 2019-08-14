
package com.bojiw.spring.demo.asyn.callback;

import com.asyncmd.callback.AbstractErrorCallBack;
import com.asyncmd.model.AsynCmd;
import org.springframework.stereotype.Service;

/**
 * @author wangwendi
 * @version $Id: AsynCmdErrorCallBack.java, v 0.1 2019年08月01日 下午7:29 wangwendi Exp $
 */
@Service("groupErrorCallBack")
public class AsynCmdErrorCallBack extends AbstractErrorCallBack {
    /**
     * 每次执行异常都会回调
     * @param asynCmd
     * @param e
     */
    @Override
    public void everyErrorCallBack(AsynCmd asynCmd, Exception e) {
        System.out.println("执行异步命令第" + asynCmd.getExecuteNum() + "次异常" + e.getMessage());
    }

    /**
     * 异步命令状态失败都时候会回调
     * @param asynCmd
     * @param e
     */
    @Override
    public void errorCallBack(AsynCmd asynCmd, Exception e) {
        System.out.println("异步命令执行失败异常" + e.getMessage());
    }
}