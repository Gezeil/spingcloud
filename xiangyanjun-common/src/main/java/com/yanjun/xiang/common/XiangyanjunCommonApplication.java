package com.yanjun.xiang.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaClient
public class XiangyanjunCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiangyanjunCommonApplication.class, args);
	}

}
