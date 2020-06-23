package com.liudehuang.item.model.request;

import com.liudehuang.common.request.MerchantIdRequest;
import lombok.Data;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/23 11:02
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/23 11:02
 * @UpdateRemark:
 * @Version:
 */
@Data
public class ItemBrandIdQueryRequest extends MerchantIdRequest {

    private Long brandId;
}
