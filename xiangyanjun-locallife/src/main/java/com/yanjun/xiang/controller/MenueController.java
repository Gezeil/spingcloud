package com.yanjun.xiang.controller;

import com.yanjun.xiang.service.MenuMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/6 0006
 */
@RestController
@RequestMapping("/menue")
@Slf4j
public class MenueController {

    @Autowired
    private MenuMainService menuMainService;

    @GetMapping(value = "/")
    public String home() {
        log.info("哟哟");
        return menuMainService.createMenu();
    }
}
