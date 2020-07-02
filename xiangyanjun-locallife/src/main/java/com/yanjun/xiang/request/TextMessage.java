package com.yanjun.xiang.request;

import com.yanjun.xiang.request.BaseMessage;
import lombok.Data;

/**
 * @Description 文本消息
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
@Data
public class TextMessage extends BaseMessage {
    // 消息内容
    private String Content;
}
