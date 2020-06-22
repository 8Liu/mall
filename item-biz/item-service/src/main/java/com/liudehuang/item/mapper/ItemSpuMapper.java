package com.liudehuang.item.mapper;

import com.liudehuang.item.model.domain.ItemSpuBase;
import com.liudehuang.item.model.domain.ItemSpuDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemSpuMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ItemSpuDetail record);

    int insertSelective(ItemSpuDetail record);

    int updateByPrimaryKeySelective(ItemSpuDetail record);

    int updateByPrimaryKey(ItemSpuDetail record);

    /**
     * 根据商品ID查询商品spu基本信息
     * @param merchantId
     * @param id
     * @return
     */
    ItemSpuBase selectByPrimaryKey(@Param("merchantId") Long merchantId, @Param("id") Long id);

    /**
     * 根据商品ID查询商品spu详情
     * @param merchantId
     * @param id
     * @return
     */
    ItemSpuDetail selectDetailByPrimaryKey(@Param("merchantId") Long merchantId, @Param("id") Long id);

    /**
     * 根据spuNo查询商品基本信息
     * @param merchantId
     * @param spuNo
     * @return
     */
    ItemSpuBase selectBySpuNo(@Param("merchantId") Long merchantId, @Param("spuNo") String spuNo);

    /**
     * 根据商品分类编号查询商品信息
     * @param merchantId
     * @param categoryNo
     * @return
     */
    List<ItemSpuBase> selectByCategoryNo(@Param("merchantId") Long merchantId, @Param("categoryNo") String categoryNo);

    /**
     * 根据商品品牌编号查询商品信息
     * @param merchantId
     * @param brandNo
     * @return
     */
    List<ItemSpuBase> selectByBrandNo(@Param("merchantId") Long merchantId, @Param("brandNo") String brandNo);

}