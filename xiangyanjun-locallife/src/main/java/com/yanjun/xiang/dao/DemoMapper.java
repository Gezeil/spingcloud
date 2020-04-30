package com.yanjun.xiang.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanjun.xiang.entity.DemoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface DemoMapper extends BaseMapper<DemoEntity> {

}
