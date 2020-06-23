package com.liudehuang.item.mapper;

import com.liudehuang.item.api.model.domain.ItemBrandBase;
import com.liudehuang.item.api.model.domain.ItemBrandDetail;
import com.liudehuang.item.model.request.ItemBrandRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemBrandMapper {

    int deleteByPrimaryKey(@Param("merchantId") Long merchantId, @Param("brandId") Long brandId);

    int insert(ItemBrandRequest record);

    int insertSelective(ItemBrandRequest record);

    int updateByPrimaryKeySelective(ItemBrandRequest record);

    int updateByPrimaryKey(ItemBrandRequest record);

    /**
     * 根据品牌ID查询品牌信息
     * @param merchantId
     * @param brandId
     * @return
     */
    ItemBrandDetail selectByPrimaryKey(@Param("merchantId") Long merchantId, @Param("brandId") Long brandId);

    /**
     * 根据品牌编号查询品牌信息
     * @param merchantId
     * @param brandNo
     * @return
     */
    ItemBrandBase selectByBrandNo(@Param("merchantId") Long merchantId, @Param("brandNo") String brandNo);

    /**
     * 根据商户号查询商品品牌信息
     * @param merchantId
     * @return
     */
    List<ItemBrandBase> selectByMerchantId(@Param("merchantId") Long merchantId);
}