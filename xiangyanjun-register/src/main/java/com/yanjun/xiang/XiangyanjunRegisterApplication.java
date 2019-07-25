package com.yanjun.xiang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class XiangyanjunRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiangyanjunRegisterApplication.class, args);
	}

}
