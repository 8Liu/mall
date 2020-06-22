package com.liudehuang.common.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 10:39
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 10:39
 * @UpdateRemark:
 * @Version:
 */
@Getter
@Setter
@ToString(callSuper = true)
public class PageResponse<T> extends Response implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作数据信息(请求结果返回)
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Integer total = 0;

    /**
     * 每页查询记录数
     */
    private Integer size = 10;

    /**
     * 当前页码
     */
    private Integer current = 1;

}