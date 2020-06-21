package com.liudehuang.item.controller;

import com.liudehuang.item.config.ItemProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
@Slf4j
@RefreshScope
public class ItemController {
    @Autowired
    private ItemProperty itemProperty;

    @GetMapping("/test")
    public String test(){
        return itemProperty.getItemNo() +":"+itemProperty.getItemName();
    }
}
