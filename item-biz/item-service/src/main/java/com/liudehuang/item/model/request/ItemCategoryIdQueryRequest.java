package com.liudehuang.item.model.request;

import com.liudehuang.common.request.MerchantIdRequest;
import lombok.Data;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 9:44
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 9:44
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemCategoryIdQueryRequest extends MerchantIdRequest {
    private Long categoryId;
}
