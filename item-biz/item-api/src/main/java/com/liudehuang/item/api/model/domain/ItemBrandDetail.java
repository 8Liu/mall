package com.liudehuang.item.api.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 11:01
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 11:01
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemBrandDetail extends ItemBrandBase {

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    private String remark;

    private Integer deleted;
}
