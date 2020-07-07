package com.yanjun.xiang.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/6 0006
 */
@Data
public class ViewButton implements Serializable {
    private String type;
    private String name;
    private String url;
}
