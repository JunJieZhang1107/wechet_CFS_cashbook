package com.jay.cfs_cashbook.mapper;

import com.jay.cfs_cashbook.bean.Borrow;

import java.math.BigDecimal;
import java.util.List;

public interface BorrowMapper { // 外借表
    // 通过accountIdLists 获取总资金
    public BigDecimal getBorrowMoneyByAccountIdList(List<Integer> list);

    // 通過accountId獲取外借資金數據
    public BigDecimal getBorrowMoneyByAccountId(Integer accountId);

    Integer save(Borrow borrow);

    // 通过accountIds 获取所有的对应的borrow数据
    List<Borrow> getBorrowsByAccountIds(List<Integer> accountIds);

    void delete(Integer id);
}
