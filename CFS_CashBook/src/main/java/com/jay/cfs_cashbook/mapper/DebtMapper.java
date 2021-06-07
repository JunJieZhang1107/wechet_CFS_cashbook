package com.jay.cfs_cashbook.mapper;

import com.jay.cfs_cashbook.bean.Debt;

import java.math.BigDecimal;
import java.util.List;

public interface DebtMapper { // 负债信息
    // 通过账户id获取所有负债信息的总和
    public BigDecimal getDebtByAccountList(List<Integer> list);

    // 通过账号id 获取 所有负债信息 以时间降序
    public List<Debt> getDebtsByAccountList(List<Integer> list);

    // 通过debtId获取accountId
    public Integer getAccountIdByDebtId(Integer debtId);

    // 通过debtId 完成数据，并扣除对应的流动资金金额
    public Integer debtPaidByDebtId(Integer debtId);

    // 通過accountId 獲取 debt_pay
    public BigDecimal getDebtPayByAccountId(Integer accountId);

    Integer insert(Debt debt);

    void updateIsPay(Integer id, boolean b);
}
