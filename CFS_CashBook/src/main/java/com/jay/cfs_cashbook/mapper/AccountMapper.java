package com.jay.cfs_cashbook.mapper;

import com.jay.cfs_cashbook.bean.Account;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.List;

public interface AccountMapper {
    // 通过user_id 查找 所有count_id数据
    public List<Integer> getAllCountIdByUserId(Integer userId);

    // 通过user_id 获取流动资金的总和
    public BigDecimal getAllFreeMoneyByUserId(Integer userId);

    // 通过user_id 查找所有account的数据
    public List<Account> getAllAccountByUserId(Integer userId);

    // 通过account_id 查找流动资金数据
    public BigDecimal getFreeMoneyByAccountId(Integer accountId);

    // 添加账户信息
    public Integer insert(Account account);

    // 删除账户信息
    public Integer delete(Integer accountId);

    // 叠加金额
    public Integer update(Account account);

    // 更新金额
    public Integer updateMoney(Account account);

    // 通过账户名去检查是否存在该数据
    public Integer accountExist(Account account);

    Integer updateDelteIncomeOutgo(Integer accountId,BigDecimal money);

    Account getAccountNameByAccountId(Integer accountId);

    void miuMoney(Integer accountId, BigDecimal money);

}
