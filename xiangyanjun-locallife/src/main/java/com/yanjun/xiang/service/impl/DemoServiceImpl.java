package com.yanjun.xiang.service.impl;

import com.yanjun.xiang.dao.DemoMapper;
import com.yanjun.xiang.entity.DemoEntity;
import com.yanjun.xiang.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public String demo() {
        DemoEntity demoEntity = new DemoEntity();
        demoEntity.setName("xyj");
        demoMapper.insert(demoEntity);
        return "demo";
    }
}
