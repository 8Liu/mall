package com.liudehuang.item.service;

import com.liudehuang.common.request.MerchantIdRequest;
import com.liudehuang.common.response.BaseResponse;
import com.liudehuang.item.api.model.domain.ItemCategoryBase;
import com.liudehuang.item.api.model.domain.ItemCategoryDetail;
import com.liudehuang.item.model.request.ItemCategoryIdQueryRequest;
import com.liudehuang.item.model.request.ItemCategoryRequest;

import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 17:17
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 17:17
 * @UpdateRemark:
 * @Version:
 */
public interface CategoryService {
    /**
     * 查询某商户的所有一级分类
     * @return
     */
    List<ItemCategoryBase> queryFirstLevelCategory(MerchantIdRequest param);

    /**
     * 根据父商品分类编号查询子商品分类
     * @param param
     * @return
     */
    List<ItemCategoryBase> queryByParentCategoryId(ItemCategoryIdQueryRequest param);

    /**
     * 新增商品分类
     * @param param
     * @return
     */
    BaseResponse insertCategory(ItemCategoryRequest param);

    /**
     * 更新商品分类
     * @param param
     * @return
     */
    BaseResponse updateCategory(ItemCategoryRequest param);

    /**
     * 根据商品分类ID查询商品分类
     * @param param
     * @return
     */
    ItemCategoryDetail queryByCategoryId(ItemCategoryIdQueryRequest param);

    /**
     * 删除商品分类
     * @param param
     * @return
     */
    BaseResponse deleteByCategoryId(ItemCategoryIdQueryRequest param);
}
