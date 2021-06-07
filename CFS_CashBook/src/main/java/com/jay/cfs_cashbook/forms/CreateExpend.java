package com.jay.cfs_cashbook.forms;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateExpend {
    private Integer budgetId; // 预算id
    private Integer accountId;  // 账号id
    private Integer budgetType; // 预算类型
    private BigDecimal spendMoney;
    private String reason;
}
