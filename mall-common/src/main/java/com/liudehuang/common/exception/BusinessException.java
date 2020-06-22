package com.liudehuang.common.exception;

import lombok.Data;
import lombok.Getter;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 11:05
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 11:05
 * @UpdateRemark:
 * @Version:
 */
@Data
public class BusinessException extends RuntimeException {

    /**
     * 自定义的异常code
     */
    private Integer code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(Integer code,String message){
        super(message);
        this.code=code;
    }

}