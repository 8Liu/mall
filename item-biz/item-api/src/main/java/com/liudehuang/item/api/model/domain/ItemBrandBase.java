package com.liudehuang.item.api.model.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class ItemBrandBase implements Serializable {
    /**
     * 商品品牌ID
     */
    private Long id;

    /**
     * 商户编号
     *
     */
    private Long merchantId;

    /**
     * 商品品牌编号
     */
    private String brandNo;

    /**
     * 商品品牌名称
     */
    private String brandName;

    /**
     * 商品品牌logo
     */
    private String brandLogo;

    /**
     * 商品品牌地址
     */
    private String brandUrl;

    /**
     * 商品品牌描述
     */
    private String brandDesc;

    /**
     * 商品品牌排序
     */
    private Integer brandSort;



}