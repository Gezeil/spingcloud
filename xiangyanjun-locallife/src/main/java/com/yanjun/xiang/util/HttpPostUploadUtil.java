package com.yanjun.xiang.util;

import com.alibaba.fastjson.JSONObject;
import com.yanjun.xiang.entity.UserInfo;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/3 0003
 */
@Component
@Slf4j
public class HttpPostUploadUtil {

    @Value("${wx.mediaUrl}")
    public String mediaUrl;

    @Value("${wx.userInfoUrl}")
    public String userInfoUrl;

    public String urlStr;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 上传图片
     *
     * @param filePath
     * @param type
     * @return
     */
    public String formUpload(String filePath, String type) throws IOException {
        String accessToken = redisTemplate.opsForValue().get("wx_access_token");
        urlStr = mediaUrl + accessToken + "&type=" + type;
        log.info(urlStr);
        File file = new File(filePath);
        HttpClient client = new DefaultHttpClient();

        HttpEntityEnclosingRequestBase request = new HttpPost(urlStr);
        //核心就是这里，添加文件参数的步骤，其他的都是小意思
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody(file.getName(), file);
        HttpEntity entity = multipartEntityBuilder.build();
        //核心到这里就结束了
        request.setEntity(entity);
        HttpResponse execute = client.execute(request);
        JSONObject jsonObject = JSONObject.parseObject(IOUtils.toString(execute.getEntity().getContent()));
        System.out.println(jsonObject);
        String typeName = "media_id";
        if (!"image".equals(type)) {
            typeName = type + "_mediaid";
        }
        String mediaId = jsonObject.getString(typeName);// 从json中获取media_id
        return mediaId;
    }

    public Map<String, String> openidUserinfo(String openid) throws Exception {
        Map<String, String> params = new HashMap<>();
        UserInfo ui = null;
        String accessToken = redisTemplate.opsForValue().get("wx_access_token");
        params.put("access_token", accessToken);  //定时器中获取到的token
        params.put("openid", openid);  //需要获取的用户的openid
        params.put("lang", "zh_CN");
        String subscribers = HttpUtils.httpClientGet(userInfoUrl, params);
        System.out.println(subscribers);
        params.clear();
        //这里返回参数只取了昵称、头像、和性别
        String nickname = JSONObject.parseObject(subscribers).getString("nickname");
        String headimgurl = JSONObject.parseObject(subscribers).getString("headimgurl");
        String sex = JSONObject.parseObject(subscribers).getString("sex");

        ui = new UserInfo(nickname, headimgurl, sex);
        params.put("nickname",
                nickname); //昵称
        params.put("headimgurl",
                headimgurl);  //图像
        params.put("sex", sex);  //性别

        return params;
    }
}
