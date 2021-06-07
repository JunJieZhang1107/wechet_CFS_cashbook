package com.jay.cfs_cashbook.forms;

import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateSpend {
    private Integer budgetId; // 预算id
    private BigDecimal eat; // 吃喝玩乐，提高
    private BigDecimal drink;
    private BigDecimal play;
    private BigDecimal funny;
    private BigDecimal improve;
    
    // 对应账户的信息
    private Integer accountId;
    private BigDecimal spendMoney;
}
