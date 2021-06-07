package com.jay.cfs_cashbook.mapper;

import com.jay.cfs_cashbook.bean.Invest;

import java.math.BigDecimal;
import java.util.List;

public interface InvestMapper { // 投资模块
    // 统计所有投资资金
    public BigDecimal getAllInvestMoneyByAccountList(List<Integer> list);

    // 通過accountId獲取賬戶投資資金
    public BigDecimal getInvestMoneyByAccountId(Integer accountId);

    Integer insert(Invest invest);

    // get all Invest list by accountId list
    public List<Invest> getAllInvestByAccountIds(List<Integer> list);

    public Invest getInvestById(Integer id);

    Integer update(Invest invest);

    List<BigDecimal> getInvestMoneyByUserId(int userId);
}
