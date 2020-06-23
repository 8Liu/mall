package com.liudehuang.item.mapper;

import com.liudehuang.item.api.model.domain.ItemCategoryBase;
import com.liudehuang.item.api.model.domain.ItemCategoryDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemCategoryMapper {

    int deleteByPrimaryKey(@Param("merchantId") Long merchantId, @Param("categoryId") Long categoryId);

    int insert(ItemCategoryBase record);

    int insertSelective(ItemCategoryBase record);

    int updateByPrimaryKeySelective(ItemCategoryBase record);

    int updateByPrimaryKey(ItemCategoryBase record);

    ItemCategoryDetail selectByPrimaryKey(@Param("merchantId") Long merchantId, @Param("categoryId") Long categoryId);

    ItemCategoryBase selectByCategoryId(@Param("merchantId") Long merchantId, @Param("categoryId") Long categoryId);

    /**
     * 根据商户号和商品类编编号查询商品类别信息
     * @param merchantId
     * @param categoryNo
     * @return
     */
    ItemCategoryBase selectByCategoryNo(@Param("merchantId") Long merchantId, @Param("categoryNo") String categoryNo);


    /**
     * 根据商户号和父商户类别编号查询商品类别信息
     * @param merchantId
     * @param parentCategoryId
     * @return
     */
    List<ItemCategoryBase> selectByParentCategoryId(@Param("merchantId")Long merchantId, @Param("parentCategoryId") Long parentCategoryId);
}