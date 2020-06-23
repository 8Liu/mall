package com.liudehuang.item.model.request;

import com.liudehuang.item.api.model.domain.ItemBrandBase;
import lombok.Data;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 11:03
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 11:03
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemBrandRequest extends ItemBrandBase {

    private String operator;
}
