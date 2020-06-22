package com.liudehuang.item.mapper;

import com.liudehuang.item.model.domain.ItemBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ItemBrandMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ItemBrand record);

    int insertSelective(ItemBrand record);

    int updateByPrimaryKeySelective(ItemBrand record);

    int updateByPrimaryKey(ItemBrand record);

    ItemBrand selectByPrimaryKey(Long id);

    /**
     * 根据品牌编号查询品牌信息
     * @param merchantId
     * @param brandNo
     * @return
     */
    ItemBrand selectByBrandNo(@Param("merchantId") Long merchantId, @Param("brandNo") String brandNo);
}