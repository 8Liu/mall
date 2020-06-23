package com.liudehuang.item.service.impl;

import com.liudehuang.common.request.MerchantIdRequest;
import com.liudehuang.common.response.BaseResponse;
import com.liudehuang.item.api.model.domain.ItemBrandBase;
import com.liudehuang.item.api.model.domain.ItemBrandDetail;
import com.liudehuang.item.mapper.ItemBrandMapper;
import com.liudehuang.item.model.request.ItemBrandIdQueryRequest;
import com.liudehuang.item.model.request.ItemBrandRequest;
import com.liudehuang.item.service.BrandService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 11:39
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 11:39
 * @UpdateRemark:
 * @Version:
 */
@Data
public class BrandServiceImpl implements BrandService {
    @Autowired
    private ItemBrandMapper brandMapper;

    @Override
    public BaseResponse insertBrand(ItemBrandRequest param) {
        return null;
    }

    @Override
    public BaseResponse updateBrand(ItemBrandRequest param) {
        return null;
    }

    @Override
    public ItemBrandDetail queryByBrandId(ItemBrandIdQueryRequest param) {
        return brandMapper.selectByPrimaryKey(param.getMerchantId(), param.getBrandId());
    }

    @Override
    public List<ItemBrandBase> queryByMerchantId(MerchantIdRequest param) {
        return null;
    }

    @Override
    public BaseResponse deleteByBrandId(ItemBrandIdQueryRequest param) {
        return null;
    }
}
