package com.liudehuang.common.exception;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 11:07
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 11:07
 * @UpdateRemark:
 * @Version:
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException() {
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}