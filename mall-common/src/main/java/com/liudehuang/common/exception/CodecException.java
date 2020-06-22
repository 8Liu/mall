package com.liudehuang.common.exception;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:19
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:19
 * @UpdateRemark:
 * @Version:
 */
public class CodecException extends RuntimeException {
    public CodecException(String msg) {
        super(msg);
    }

    public CodecException(Throwable throwable) {
        super(throwable);
    }

    public CodecException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
