package com.jay.cfs_cashbook.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@ApiModel("账户信息")
public class Account { // 账户表
    @ApiModelProperty("账户id")
    private Integer accountId; // 账户Id
    @ApiModelProperty("用户id")
    private Integer userId; // 用户Id
    @ApiModelProperty("账户名")
    private String accountName; // 账户名
    @ApiModelProperty("流动资金")
    private BigDecimal accountFreeMoney; // 流动资金
}
