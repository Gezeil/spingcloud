package com.yanjun.xiang.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yanjun.xiang.entity.ClickButton;
import com.yanjun.xiang.entity.ViewButton;
import com.yanjun.xiang.service.MenuMainService;
import com.yanjun.xiang.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/6 0006
 */
@Service
public class MenuMainServiceImpl implements MenuMainService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String createMenu() {
        ClickButton cbt = new ClickButton();
        cbt.setKey("image");
        cbt.setName("回复图片");
        cbt.setType("click");
//        cbt.setType("subscribe");

        ViewButton vbt = new ViewButton();
        vbt.setUrl("http://3rva7u.natappfree.cc/test/hello");
        vbt.setName("大头");
        vbt.setType("view");

        JSONArray sub_button = new JSONArray();
        sub_button.add(cbt);
        sub_button.add(vbt);
        JSONArray parse = (JSONArray) JSONObject.parse(JSONArray.toJSONString(sub_button, SerializerFeature.DisableCircularReferenceDetect));
        JSONObject buttonOne = new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", parse);

        JSONArray button = new JSONArray();
        button.add(vbt);
        button.add(buttonOne);
        button.add(cbt);

        JSONObject menujson = new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson);

        String accessToken = redisTemplate.opsForValue().get("wx_access_token");
        //这里为请求接口的url   +号后面的是token，
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;
        try {
            String rs = HttpUtils.httpClientPostString(url, menujson.toJSONString());
            System.out.println(rs);
            return rs;
        } catch (
                Exception e) {
            System.out.println("请求错误！");
        }
        return null;
    }
}
