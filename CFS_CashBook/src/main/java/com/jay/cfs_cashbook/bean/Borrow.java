package com.jay.cfs_cashbook.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@ApiModel("资金外借信息")
public class Borrow { // 外借表
    @ApiModelProperty("资金外借id")
    private Integer borrowId; // 外借id
    @ApiModelProperty("账户id")
    private Integer accountId; // 账户id
    @ApiModelProperty("外借资金")
    private BigDecimal borrowMoney; // 外借金额
    @ApiModelProperty("外借人的名字")
    private String borrowName; // 外借名
    @ApiModelProperty("资金返回时间")
    private Date backTime; // 返回时间

}
