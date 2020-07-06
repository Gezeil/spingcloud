package com.yanjun.xiang.service;

import java.io.IOException;
import java.util.Map;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/6 0006
 */
public interface DispatcherService {
    String processEvent(Map<String, String> map) throws IOException;

    String processMessage(Map<String, String> map) throws IOException;
}
