package com.liudehuang.item.service.impl;

import com.liudehuang.common.constant.CommonConstant;
import com.liudehuang.common.request.MerchantIdRequest;
import com.liudehuang.common.response.BaseResponse;
import com.liudehuang.common.utils.ResponseUtil;
import com.liudehuang.item.api.model.domain.ItemCategoryBase;
import com.liudehuang.item.api.model.domain.ItemCategoryDetail;
import com.liudehuang.item.mapper.ItemCategoryMapper;
import com.liudehuang.item.model.request.ItemCategoryIdQueryRequest;
import com.liudehuang.item.model.request.ItemCategoryRequest;
import com.liudehuang.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 10:23
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 10:23
 * @UpdateRemark:
 * @Version:
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ItemCategoryMapper categoryMapper;

    @Override
    public List<ItemCategoryBase> queryFirstLevelCategory(MerchantIdRequest param) {
        return categoryMapper.selectByParentCategoryId(param.getMerchantId(), CommonConstant.LONG_0);
    }

    @Override
    public List<ItemCategoryBase> queryByParentCategoryId(ItemCategoryIdQueryRequest param) {
        return categoryMapper.selectByParentCategoryId(param.getMerchantId(),param.getCategoryId());
    }

    @Override
    public BaseResponse insertCategory(ItemCategoryRequest param) {
        return null;
    }

    @Override
    public BaseResponse updateCategory(ItemCategoryRequest param) {
        return null;
    }

    @Override
    public ItemCategoryDetail queryByCategoryId(ItemCategoryIdQueryRequest param) {
        return categoryMapper.selectByPrimaryKey(param.getMerchantId(), param.getCategoryId());
    }

    @Override
    public BaseResponse deleteByCategoryId(ItemCategoryIdQueryRequest param) {
        Integer row = categoryMapper.deleteByPrimaryKey(param.getMerchantId(), param.getCategoryId());
        return ResponseUtil.setDisposeDataResponse(row, "删除商品分类失败");
    }
}
