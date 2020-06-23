package com.liudehuang.common.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: liudh
 * @CreateDate: 2020/6/22 19:32
 * @UpdateUser: liudh
 * @UpdateDate: 2020/6/22 19:32
 * @UpdateRemark:
 * @Version:
 */
@Data
public class MerchantIdRequest implements Serializable {

    private static final long serialVersionUID = -4884713251350060095L;

    private Long merchantId;
}
