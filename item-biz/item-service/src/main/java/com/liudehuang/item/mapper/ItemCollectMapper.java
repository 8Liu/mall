package com.liudehuang.item.mapper;

import com.liudehuang.item.model.domain.ItemCollect;

public interface ItemCollectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_collect
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_collect
     *
     * @mbggenerated
     */
    int insert(ItemCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_collect
     *
     * @mbggenerated
     */
    int insertSelective(ItemCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_collect
     *
     * @mbggenerated
     */
    ItemCollect selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_collect
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(ItemCollect record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_item_collect
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(ItemCollect record);
}