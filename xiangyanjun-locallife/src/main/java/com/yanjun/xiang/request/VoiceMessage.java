package com.yanjun.xiang.request;

import com.yanjun.xiang.request.BaseMessage;
import lombok.Data;

/**
 * @Description 语音消息
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
@Data
public class VoiceMessage extends BaseMessage {
    // 媒体 ID
    private String MediaId;
    // 语音格式
    private String Format;
}
