package com.liudehuang.common.exception;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:31
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:31
 * @UpdateRemark:
 * @Version:
 */
public class AuthenticationException extends RuntimeException{

    public AuthenticationException(String message) {
        super(message);
    }

}
