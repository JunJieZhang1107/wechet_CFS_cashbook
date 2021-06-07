package com.jay.cfs_cashbook.views;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.Invest;
import lombok.Data;

@Data
public class InvestView {
    private Invest invest;
    private Account account;
}
