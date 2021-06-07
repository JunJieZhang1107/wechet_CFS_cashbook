package com.jay.cfs_cashbook.views;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DebtView {
    private Integer accountId;
    private Integer id;
    private String accountName;
    private BigDecimal debtPay;
    private long payTime;
}
