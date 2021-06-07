package com.jay.cfs_cashbook.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
@ApiModel("用户信息")
public class User {
    @ApiModelProperty("用户唯一值")
    private Integer userId;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("用户密码")
    private String password;
}
