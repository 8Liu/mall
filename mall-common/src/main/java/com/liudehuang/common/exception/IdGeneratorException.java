package com.liudehuang.common.exception;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 11:06
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 11:06
 * @UpdateRemark:
 * @Version:
 */
public class IdGeneratorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IdGeneratorException(String message) {
        super(message);
    }

    public IdGeneratorException(Throwable throwable) {
        super(throwable);
    }

    public IdGeneratorException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
