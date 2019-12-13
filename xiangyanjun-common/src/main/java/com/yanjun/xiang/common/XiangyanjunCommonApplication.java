package com.yanjun.xiang.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.yanjun.xiang.common.service.api")
public class XiangyanjunCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiangyanjunCommonApplication.class, args);
	}

}
