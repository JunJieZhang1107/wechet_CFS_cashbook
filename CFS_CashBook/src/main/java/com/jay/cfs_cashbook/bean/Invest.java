package com.jay.cfs_cashbook.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@ApiModel("投资信息")
public class Invest { // 投资表
    @ApiModelProperty("投资id")
    private Integer investId; // 投资主键
    @ApiModelProperty("用户id")
    private Integer accountId; // 用户id
    @ApiModelProperty("投资产品名")
    private String productName; // 投资产品名
    @ApiModelProperty("投资类型：1.基金 2. 股票 3. 定期")
    private Integer investType; // 投资类型 1. 基金 2. 股票  3. 定期
    @ApiModelProperty("投资资金")
    private BigDecimal investMoney; // 投资金额
    @ApiModelProperty("回款时间")
    private Date backTime; // 回款时间
    @ApiModelProperty("回款金额")
    private BigDecimal backMoney; // 回款金额
    @ApiModelProperty("投资金额 - 回款金额")
    private BigDecimal minuDiff; // 两者差值
}
