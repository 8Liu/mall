package com.liudehuang.item.api.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemCategoryBase implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 商户号
     */
    private Long merchantId;

    /**
     * 商品分类编号
     */
    private String categoryNo;

    /**
     * 商品分类图标
     */
    private String categoryIcon;

    /**
     * 商品分类图片
     */
    private String categoryPic;

    /**
     * 父商品分类ID（如果没有则是二级分类）
     */
    private String parentCategoryId;

    /**
     * 商品分类等级(1：一级分类 2：二级分类)
     */
    private Integer categoryLevel;

    /**
     * 商品分类排序
     */
    private Integer categorySort;

    /**
     * 商品分类名称
     */
    private String categoryName;



}