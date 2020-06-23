package com.liudehuang.item.api.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 14:58
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 14:58
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemSpuBase implements Serializable {
    /**
     * 商品ID
     */
    private Long id;
    /**
     * 商户号
     */
    private Long merchantId;
    /**
     * 商品编号
     */
    private String itemNo;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 商品分类编号
     */
    private String categoryNo;
    /**
     * 商品品牌编号
     */
    private String brandNo;
    /**
     * 商品图片
     */
    private String itemPic;
    /**
     * 商品关键词
     */
    private String itemKeyword;
    /**
     * 商品单位
     */
    private String itemUnit;
    /**
     * 商品简介
     */
    private String itemBrief;
    /**
     * 是否上架（0：未上架 1:上架）
     */
    private Integer isSale;
    /**
     * 是否新品首发（0：不是新品首发 1：新品首发）
     */
    private Integer isNew;
    /**
     * 是否人气推荐
     */
    private Integer isHot;
}
