package com.jay.cfs_cashbook.forms;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateBorrow {
    private Integer id;
    private Integer accountId;
    private BigDecimal borrowMoney;
}
