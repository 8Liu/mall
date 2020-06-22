package com.liudehuang.common.exception;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 11:05
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 11:05
 * @UpdateRemark:授权异常
 * @Version:
 */
public class AuthorizationException extends RuntimeException {

    public AuthorizationException(String message) {
        super(message);
    }

}