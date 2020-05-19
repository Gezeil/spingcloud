package com.yanjun.xiang.common.util;

import com.alibaba.fastjson.JSON;

import java.util.Objects;

public class JsonUtils {
    public static String toJsonString(Object target){
        if (Objects.isNull(target)) {
            return "";
        } else {
            return JSON.toJSONString(target);
        }
    }
}
