package com.liudehuang.item.service;

import com.liudehuang.common.request.MerchantIdRequest;
import com.liudehuang.common.response.BaseResponse;
import com.liudehuang.item.api.model.domain.ItemBrandBase;
import com.liudehuang.item.api.model.domain.ItemBrandDetail;
import com.liudehuang.item.model.request.ItemBrandIdQueryRequest;
import com.liudehuang.item.model.request.ItemBrandRequest;

import java.util.List;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 10:48
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 10:48
 * @UpdateRemark:
 * @Version:
 */
public interface BrandService {
    /**
     * 新增商品品牌
     * @param param
     * @return
     */
    BaseResponse insertBrand(ItemBrandRequest param);

    /**
     * 更新商品品牌
     * @param param
     * @return
     */
    BaseResponse updateBrand(ItemBrandRequest param);

    /**
     * 根据商品品牌ID查询商品品牌
     * @param param
     * @return
     */
    ItemBrandDetail queryByBrandId(ItemBrandIdQueryRequest param);

    /**
     * 根据商户号查询该商户下的所有品牌
     * @param param
     * @return
     */
    List<ItemBrandBase> queryByMerchantId(MerchantIdRequest param);

    /**
     * 根据商品品牌ID删除商品品牌
     * @param param
     * @return
     */
    BaseResponse deleteByBrandId(ItemBrandIdQueryRequest param);
}
