package com.liudehuang.item.model.domain;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 15:30
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 15:30
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemSpuDetail extends ItemSpuBase {
    /**
     * 商品走马灯图片
     */
    private String itemGallery;

    /**
     * 商品详情
     */
    private String itemDetail;

    private Date createdTime;

    private String createdBy;

    private Date updatedTime;

    private String updatedBy;

    private String remark;

    private Integer deleted;

}
