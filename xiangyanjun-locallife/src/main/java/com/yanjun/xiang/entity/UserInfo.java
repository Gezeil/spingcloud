package com.yanjun.xiang.entity;

import lombok.Data;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2020/7/7 0007
 */
@Data
public class UserInfo {

    public String nickname;    //用户昵称
    public String headimgurl;  //头像
    public String sex;            //性别

    public UserInfo(String nickname, String headimgurl, String sex) {
        this.nickname = nickname;
        this.headimgurl = headimgurl;
        this.sex = sex;
    }
}
