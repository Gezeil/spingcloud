package com.yanjun.xiang.response;

import lombok.Data;

import java.util.List;

/**
 * @Description 图文回复
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/2 0002
 */
@Data
public class NewsMessage extends BaseMessage {
    // 图文消息个数，限制为10条以内
    private int ArticleCount;
    // 多条图文消息信息，默认第一个item为大图
    private List<Article> Articles;
}
