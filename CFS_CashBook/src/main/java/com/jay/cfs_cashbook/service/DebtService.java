package com.jay.cfs_cashbook.service;

import com.jay.cfs_cashbook.bean.Debt;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.DebtMapper;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DebtService {
    @Autowired
    AccountMapper accountMapper; // 用于获取所有accountId
    @Autowired
    DebtMapper debtMapper; // 负债信息
    // 通过accountId 获取负债信息
    @Transactional(readOnly = true)
    public List<Debt> getDebtsListByAccountIds(Integer userId){ // 获取所有负债数据
        // 获取 该用户下所有accountId
        List<Integer> accountIdList = accountMapper.getAllCountIdByUserId(userId);
        List<Debt> debtsByAccountList = debtMapper.getDebtsByAccountList(accountIdList);
        return debtsByAccountList;
    }

    // 提交数据
    public Map<String,String> insertDebt(Debt debt){
        Map<String,String> map  = new HashMap<>();

        return map;
    }

    // 已归还金额 在对应
}
