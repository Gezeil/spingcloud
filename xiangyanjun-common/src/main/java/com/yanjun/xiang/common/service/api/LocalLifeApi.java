package com.yanjun.xiang.common.service.api;

import com.yanjun.xiang.common.annotation.GetApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author XiangYanJun
 * @Date 2019/8/2 0002.
 */

@FeignClient(name = "xiangyanjun-locallife")
public interface LocalLifeApi {

    @GetApi(value = "/test/hello")
    public void hello();

}
