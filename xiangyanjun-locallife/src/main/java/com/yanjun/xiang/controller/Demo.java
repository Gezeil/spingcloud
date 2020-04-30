package com.yanjun.xiang.controller;

import com.yanjun.xiang.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/demo")
@Api(value = "demo" , tags = "demo")
public class Demo {

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/demo")
    @ApiOperation(value = "demo")
    public String demo(){
        return demoService.demo();
    }
}
