package com.liudehuang.item.mapper;

import com.liudehuang.item.model.domain.ItemSku;

public interface ItemSkuMapper {

    int deleteByPrimaryKey(Long id);


    int insert(ItemSku record);


    int insertSelective(ItemSku record);


    ItemSku selectByPrimaryKey(Long id);


    int updateByPrimaryKeySelective(ItemSku record);


    int updateByPrimaryKey(ItemSku record);
}