package com.liudehuang.item.mapper;

import com.liudehuang.item.model.domain.ItemCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemCategoryMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ItemCategory record);

    int insertSelective(ItemCategory record);

    int updateByPrimaryKeySelective(ItemCategory record);

    int updateByPrimaryKey(ItemCategory record);

    ItemCategory selectByPrimaryKey(Long id);

    /**
     * 根据商户号和商品类编编号查询商品类别信息
     * @param merchantId
     * @param categoryNo
     * @return
     */
    ItemCategory selectByCategoryNo(@Param("merchantId") Long merchantId, @Param("categoryNo") String categoryNo);

    /**
     * 根据商户号和父商户类别编号查询商品类别信息
     * @param merchantId
     * @param parentCategoryId
     * @return
     */
    List<ItemCategory> selectByParentCategoryId(@Param("merchantId")Long merchantId, @Param("parentCategoryId") String parentCategoryId);
}