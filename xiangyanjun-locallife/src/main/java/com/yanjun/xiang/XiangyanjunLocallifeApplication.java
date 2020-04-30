package com.yanjun.xiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.yanjun.xiang.dao")
public class XiangyanjunLocallifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiangyanjunLocallifeApplication.class, args);
	}

}
