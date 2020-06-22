package com.liudehuang.item.mapper;

import com.liudehuang.item.model.domain.ItemParam;

public interface ItemParamMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ItemParam record);

    int insertSelective(ItemParam record);

    ItemParam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemParam record);

    int updateByPrimaryKey(ItemParam record);
}