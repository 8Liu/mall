package com.liudehuang.item.model.request;

import com.liudehuang.item.model.domain.ItemCategoryBase;
import lombok.Data;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 10:15
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 10:15
 * @UpdateRemark: 新增/更新商品分类参数
 * @Version:
 */
@Data
public class ItemCategoryRequest extends ItemCategoryBase {

    private String operator;
}
