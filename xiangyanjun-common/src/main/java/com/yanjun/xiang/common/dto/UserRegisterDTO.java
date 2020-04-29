package com.yanjun.xiang.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegisterDTO {

    @ApiModelProperty(value = "账号", name = "userName", dataType = "String")
    @NotNull(message = "账号不能为空")
    private String userName;

    @ApiModelProperty(value = "密码", name = "password", dataType = "String")
    @NotNull(message = "密码不能为空")
    private String password;

}
