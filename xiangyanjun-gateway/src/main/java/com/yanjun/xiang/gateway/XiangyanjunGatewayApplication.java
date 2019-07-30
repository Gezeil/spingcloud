package com.yanjun.xiang.gateway;


import com.spring4all.swagger.EnableSwagger2Doc;
import com.yanjun.xiang.gateway.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@EnableSwagger2Doc
public class XiangyanjunGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(XiangyanjunGatewayApplication.class, args);
	}

//	@Bean
//	public TokenFilter tokenFilter(){
//		return new TokenFilter();
//	}

	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {
		@Override
		public List<SwaggerResource> get() {
			List resources = new ArrayList<>();
			resources.add(swaggerResource("locallife", "/locallife/v2/api-docs", "2.0"));
			resources.add(swaggerResource("common", "/common/v2/api-docs", "2.0"));
			return resources;
		}

		private SwaggerResource swaggerResource(String name, String location, String version) {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setLocation(location);
			swaggerResource.setSwaggerVersion(version);
			return swaggerResource;
		}
	}

}
