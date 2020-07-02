package com.yanjun.xiang.request;

import com.yanjun.xiang.request.BaseMessage;
import lombok.Data;

/**
 * @Description 视频/小视屏消息
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
@Data
public class VideoMessage extends BaseMessage {
    private String MediaId; // 视频消息媒体 id，可以调用多媒体文件下载接口拉取数据
    private String ThumbMediaId; // 视频消息缩略图的媒体 id，可以调用多媒体文件下载接口拉取数据
}
