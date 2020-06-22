package com.liudehuang.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:37
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:37
 * @UpdateRemark:
 * @Version:
 */
@Getter
@Setter
@ToString
public class Response implements Serializable {

    private static final long serialVersionUID = 7258031428030068691L;

    /**
     * 请求返回结果编码
     */
    protected Integer returnCode;

    /**
     * 请求返回结果消息
     */
    protected String message;

}
