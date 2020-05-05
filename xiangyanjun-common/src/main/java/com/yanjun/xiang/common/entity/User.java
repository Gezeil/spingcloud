package com.yanjun.xiang.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_info")
public class User {
    private Long id;
    private String name;
    private String phone;
    private String password;
    @TableField(exist = false)
    private String per;
    private String salt;
}
