package com.yanjun.xiang.dispather;

import com.yanjun.xiang.request.TextMessage;
import com.yanjun.xiang.response.Article;
import com.yanjun.xiang.response.NewsMessage;
import com.yanjun.xiang.util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 消息分类处理
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
public class MsgDispatcher {
    public static String processMessage(Map<String, String> map) {
        String openid = map.get("FromUserName"); //用户openid
        String mpid = map.get("ToUserName");   //公众号原始ID
        //普通文本消息
        TextMessage txtmsg = new TextMessage();
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
            txtmsg.setToUserName(openid);
            txtmsg.setFromUserName(mpid);
            txtmsg.setCreateTime(new Date().getTime());
            txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            txtmsg.setContent("你好，欢迎您的关注！");
            return MessageUtil.textMessageToXml(txtmsg);
        }
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
            NewsMessage newmsg = new NewsMessage();
            newmsg.setToUserName(openid);
            newmsg.setFromUserName(mpid);
            newmsg.setCreateTime(new Date().getTime());
            newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            System.out.println("==============这是图片消息！");
            Article article = new Article();
            article.setDescription("这是图文消息1"); //图文消息的描述
            article.setPicUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593695482131&di=eba099c29fe28978ff04dee784cb83d1&imgtype=0&src=http%3A%2F%2Fwww.289.com%2Fup%2F2018-11%2F20181127181246542640.jpg"); //图文消息图片地址
            article.setTitle("图文消息1");  //图文消息标题
            article.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593695482131&di=eba099c29fe28978ff04dee784cb83d1&imgtype=0&src=http%3A%2F%2Fwww.289.com%2Fup%2F2018-11%2F20181127181246542640.jpg");  //图文url链接
            List<Article> list = new ArrayList<Article>();
            list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
            newmsg.setArticleCount(list.size());
            newmsg.setArticles(list);
            return MessageUtil.newsMessageToXml(newmsg);
        }
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
            System.out.println("==============这是链接消息！");
        }
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
            System.out.println("==============这是位置消息！");
        }
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
            System.out.println("==============这是视频消息！");
        }
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            System.out.println("==============这是语音消息！");
        }

        return null;
    }
}
