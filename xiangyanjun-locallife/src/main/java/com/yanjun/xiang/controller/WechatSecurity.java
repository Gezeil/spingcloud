package com.yanjun.xiang.controller;

import com.yanjun.xiang.dispather.EventDispatcher;
import com.yanjun.xiang.dispather.MsgDispatcher;
import com.yanjun.xiang.util.MessageUtil;
import com.yanjun.xiang.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatSecurity {
    @GetMapping(value = "security")
    public String security(@RequestParam("signature") String signature,
                           @RequestParam("timestamp") String timestamp,
                           @RequestParam("nonce") String nonce,
                           @RequestParam("echostr") String echostr) {
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                return echostr;
            } else {
                log.info("这里存在非法请求！");
                return null;
            }
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @PostMapping(value = "security")
    public String securityPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String> map = MessageUtil.parseXml(request);
            String msgtype = map.get("MsgType");
            if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)) {
                return EventDispatcher.processEvent(map);
            } else {
                return MsgDispatcher.processMessage(map);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
