package com.jay.cfs_cashbook.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@ApiModel("负债信息")
public class Debt { // 负债表
    @ApiModelProperty("账户id")
    private Integer accountId; // 账户id
    @ApiModelProperty("负债id")
    private Integer debtId; // 负债id
    @ApiModelProperty("负债偿还金额")
    private BigDecimal debtPay; // 负债偿还的金额
    @ApiModelProperty("是否偿还：false、true")
    private boolean isPay; // 是否偿还 0 未偿还  1 已偿还
    @ApiModelProperty("偿还时间")
    private Date payTime; // 付款时间
}
