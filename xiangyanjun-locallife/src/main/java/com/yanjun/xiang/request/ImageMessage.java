package com.yanjun.xiang.request;

import com.yanjun.xiang.request.BaseMessage;
import lombok.Data;

/**
 * @Description 图片消息
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
@Data
public class ImageMessage extends BaseMessage {
    // 图片链接
    private String PicUrl;
}
