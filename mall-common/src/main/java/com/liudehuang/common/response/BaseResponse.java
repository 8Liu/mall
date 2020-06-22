package com.liudehuang.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:38
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:38
 * @UpdateRemark:
 * @Version:
 */
@Getter
@Setter
@ToString(callSuper = true)
public class BaseResponse<T> extends Response implements Serializable {

    private static final long serialVersionUID = 8033563440470167781L;

    /**
     * 操作数据信息(请求结果返回)
     */
    protected T dataInfo;

}