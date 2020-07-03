package com.yanjun.xiang.dispather;

import com.alibaba.fastjson.JSONObject;
import com.yanjun.xiang.response.Image;
import com.yanjun.xiang.response.ImageMessage;
import com.yanjun.xiang.util.HttpPostUploadUtil;
import com.yanjun.xiang.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 事件消息的业务分发处理
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
@Component
public class EventDispatcher {

    @Autowired
    private HttpPostUploadUtil httpPostUploadUtil;

    public  String processEvent(Map<String, String> map) {
        String openid = map.get("FromUserName"); // 用户openid
        String mpid = map.get("ToUserName"); // 公众号原始ID
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
            ImageMessage imgmsg = new ImageMessage();
            imgmsg.setToUserName(openid);
            imgmsg.setFromUserName(mpid);
            imgmsg.setCreateTime(new Date().getTime());
            imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
            System.out.println("==============这是关注事件！");
            Image img = new Image();
            String filepath = "D:\\1.gif";
            Map<String, String> textMap = new HashMap<>();
            textMap.put("name", "testname");
            Map<String, String> fileMap = new HashMap<>();
            fileMap.put("userfile", filepath);
            String mediaidrs = httpPostUploadUtil.formUpload(textMap, fileMap);
            System.out.println(mediaidrs);
            String mediaid = JSONObject.parseObject(mediaidrs).getString("media_id");
            img.setMediaId(mediaid);
            imgmsg.setImage(img);
            return MessageUtil.imageMessageToXml(imgmsg);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { // 取消关注事件
            System.out.println("==============这是取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { // 扫描二维码事件
            System.out.println("==============这是扫描二维码事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { // 位置上报事件
            System.out.println("==============这是位置上报事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { // 自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { // 自定义菜单View事件
            System.out.println("==============这是自定义菜单View事件！");
        }

        return null;
    }
}
