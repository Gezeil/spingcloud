package com.yanjun.xiang.task;

import com.alibaba.fastjson.JSONObject;
import com.yanjun.xiang.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/3 0003
 */
@EnableScheduling
@Component
public class WeChatTask {
    /**
     * 18      * @Description: 任务执行体
     * 19      * @param @throws Exception
     * 20
     */
    @Value("${wx.appid}")
    public String appid;

    @Value("${wx.AppSecret}")
    public String appSecret;

    @Value("${wx.tokenUrl}")
    public String tokenUrl;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostConstruct
    @Scheduled(cron = "0 0 */2 * * ?")
    public void getToken_getTicket() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credential");
        params.put("appid", appid);
        params.put("secret", appSecret);
        String jstoken = HttpUtils.sendGet(
                tokenUrl, params);
        String access_token = JSONObject.parseObject(jstoken).getString(
                "access_token"); // 获取到token并赋值保存
        redisTemplate.opsForValue().set("wx_access_token",access_token,2L, TimeUnit.HOURS);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "token为==============================" + access_token);
    }
}
