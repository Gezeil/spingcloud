package com.yanjun.xiang.controller;

import com.yanjun.xiang.service.DispatcherService;
import com.yanjun.xiang.util.MessageUtil;
import com.yanjun.xiang.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/wechat")
@Slf4j
public class WechatSecurity {

    @Autowired
    private DispatcherService dispatcherService;

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
                return dispatcherService.processEvent(map);
            } else {
                return dispatcherService.processMessage(map);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        return null;
    }
}
