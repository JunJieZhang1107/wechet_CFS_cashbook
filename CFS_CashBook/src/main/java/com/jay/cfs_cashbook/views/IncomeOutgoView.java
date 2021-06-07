package com.jay.cfs_cashbook.views;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.IncomeOutgo;
import lombok.Data;

@Data
public class IncomeOutgoView {
    private Account account;
    private IncomeOutgo incomeOutgo;
}
