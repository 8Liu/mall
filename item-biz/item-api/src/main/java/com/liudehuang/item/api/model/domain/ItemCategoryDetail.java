package com.liudehuang.item.api.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 10:14
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 10:14
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemCategoryDetail extends ItemCategoryBase {
    /**
     *
     */
    private Date createdTime;

    /**
     *
     */
    private String createdBy;

    /**
     *
     */
    private Date updatedTime;

    /**
     *
     */
    private String updatedBy;

    /**

     */
    private String remark;

    /**
     *
     * @mbggenerated
     */
    private Integer deleted;
}
