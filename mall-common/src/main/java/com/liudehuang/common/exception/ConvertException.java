package com.liudehuang.common.exception;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:04
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:04
 * @UpdateRemark:
 * @Version:
 */
public class ConvertException extends RuntimeException {
    public ConvertException(String msg) {
        super(msg);
    }

    public ConvertException(Throwable throwable) {
        super(throwable);
    }

    public ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }
}