package com.yanjun.xiang.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix="elasticsearch")
@Getter
@Setter
public class ElasticSearchConfig {


    private List<String> hostlist;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        List<HttpHost> httpHostList = new ArrayList<>(hostlist.size());
        //封装es服务端地址
        for(String host:hostlist){
            HttpHost httpHost = new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), "http");
            httpHostList.add(httpHost);
        }
        return new RestHighLevelClient(RestClient.builder(httpHostList.toArray(new HttpHost[0])));
    }

    //把低级客户端也注入，但是基本不用
    @Bean
    public RestClient restClient(){
        List<HttpHost> httpHostList = new ArrayList<>(hostlist.size());
        //封装es服务端地址
        for(String host:hostlist){
            HttpHost httpHost = new HttpHost(host.split(":")[0], Integer.parseInt(host.split(":")[1]), "http");
            httpHostList.add(httpHost);
        }
        return RestClient.builder(httpHostList.toArray(new HttpHost[0])).build();
    }

}