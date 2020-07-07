package com.yanjun.xiang.service.impl;

import com.yanjun.xiang.request.TextMessage;
import com.yanjun.xiang.response.*;
import com.yanjun.xiang.service.DispatcherService;
import com.yanjun.xiang.util.HttpPostUploadUtil;
import com.yanjun.xiang.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/6 0006
 */
@Service
public class DispatcherServiceImpl implements DispatcherService {

    @Autowired
    private HttpPostUploadUtil httpPostUploadUtil;

    /**
     * 事件
     *
     * @param map
     * @return
     */
    @Override
    public String processEvent(Map<String, String> map) throws Exception {
        String openid = map.get("FromUserName"); // 用户openid
        String mpid = map.get("ToUserName"); // 公众号原始ID
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { // 关注事件
            NewsMessage newmsg = new NewsMessage();
            newmsg.setToUserName(openid);
            newmsg.setFromUserName(mpid);
            newmsg.setCreateTime(new Date().getTime());
            newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            Map<String, String> userinfo = httpPostUploadUtil.openidUserinfo(openid);
            Article article = new Article();
            article.setTitle("菜鸟程序员成长之路！");
            article.setUrl("http://3rva7u.natappfree.cc/test/hello");
            article.setPicUrl(String.valueOf(userinfo.get("headimgurl")));
            article.setTitle("尊敬的：" + userinfo.get("nickname") + ",你好！");
            List<Article> list = new ArrayList<Article>();
            list.add(article); // 这里发送的是单图文，如果需要发送多图文则在这里list中加入多个Article即可！
            newmsg.setArticleCount(list.size());
            newmsg.setArticles(list);
            return MessageUtil.newsMessageToXml(newmsg);
//            ImageMessage imgmsg = new ImageMessage();
//            imgmsg.setToUserName(openid);
//            imgmsg.setFromUserName(mpid);
//            imgmsg.setCreateTime(new Date().getTime());
//            imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
//            System.out.println("==============这是关注事件！");
//            Image img = new Image();
//            String filepath = "D:\\1.gif";
//            String type = "image";
//            String mediaid = httpPostUploadUtil.formUpload(filepath, type);
//            System.out.println(mediaid);
//            img.setMediaId(mediaid);
//            imgmsg.setImage(img);
//            return MessageUtil.imageMessageToXml(imgmsg);
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
            ImageMessage imgmsg = new ImageMessage();
            imgmsg.setToUserName(openid);
            imgmsg.setFromUserName(mpid);
            imgmsg.setCreateTime(new Date().getTime());
            imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
            System.out.println("==============这是关注事件！");
            Image img = new Image();
            String filepath = "D:\\1.gif";
            String type = "image";
            String mediaid = httpPostUploadUtil.formUpload(filepath, type);
            System.out.println(mediaid);
            img.setMediaId(mediaid);
            imgmsg.setImage(img);

            System.out.println("==============这是自定义菜单点击事件！");
            return MessageUtil.imageMessageToXml(imgmsg);
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { // 自定义菜单View事件
            System.out.println("==============这是自定义菜单View事件！");
        }

        return null;
    }

    /**
     * 消息
     *
     * @param map
     * @return
     */
    @Override
    public String processMessage(Map<String, String> map) throws IOException {
        String openid = map.get("FromUserName"); //用户openid
        String mpid = map.get("ToUserName");   //公众号原始ID
        //普通文本消息
        TextMessage txtmsg = new TextMessage();
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
            StringBuffer sb = new StringBuffer();
            String content = map.get("Content");
            sb.append("欢迎关注老向公众号：\n\n ");
            sb.append("1、听首歌吧   \n\n ");
            sb.append("2、语音回复   \n\n ");
            sb.append("回复?调出主菜单哦哦   \n ");
            String context = sb.toString();
            txtmsg.setContent(context);
            txtmsg.setToUserName(openid);
            txtmsg.setFromUserName(mpid);
            txtmsg.setCreateTime(new Date().getTime());
            txtmsg.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
            if ("1".equals(content)) {
                MusicMessage mucmsg = new MusicMessage();
                mucmsg.setToUserName(openid);
                mucmsg.setFromUserName(mpid);
                mucmsg.setCreateTime(new Date().getTime());
                mucmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_MUSIC);

                Music music = new Music();
                String filepath = "D:\\22222.jpg";
                String type = "thumb";
                String mediaid = httpPostUploadUtil.formUpload(filepath, type);
                System.out.println(mediaid);
                music.setTitle("一路向北");
                music.setThumbMediaId(mediaid);
                music.setDescription("一路向北——向延俊");
                music.setMusicUrl("http://music.migu.cn/v3/music/album/8591");
                music.setHQMusicUrl("http://music.migu.cn/v3/music/album/8591");
//                music.setMusicUrl("http://music.163.com/#/song?id=31877628");
//                music.setHQMusicUrl("http://music.163.com/#/song?id=31877628");
                mucmsg.setMusic(music);
                return MessageUtil.musicMessageToXml(mucmsg);
            } else if ("2".equals(content)) {
                txtmsg.setContent("语音回复！");
            } else if ("?".equals(content)) {
                txtmsg.setContent(context);
            } else {
                txtmsg.setContent("你好，欢迎来到gede博客！");
            }
            String s = MessageUtil.textMessageToXml(txtmsg);
            return s;
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
