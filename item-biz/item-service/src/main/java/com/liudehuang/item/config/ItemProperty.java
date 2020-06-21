package com.liudehuang.item.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "mall.item")
public class ItemProperty {

    private String itemNo;

    private String itemName;


}
