package com.jay.cfs_cashbook.service;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.forms.CreateSpend;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.BorrowMapper;
import com.jay.cfs_cashbook.mapper.DebtMapper;
import com.jay.cfs_cashbook.mapper.InvestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountService { // 账户服务
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    BorrowMapper borrowMapper; // 外借资金
    @Autowired
    InvestMapper investMapper; // 投资模块
    @Autowired
    DebtMapper debtMapper; // 负债模块

    // 获取所有账户的信息
    public List<Account> getAllAccount(Integer userId){
        List<Account> allAccountByUserId = accountMapper.getAllAccountByUserId(userId);
        return allAccountByUserId;
    }

    // 获取账户的freeMoney
    public Map<String, BigDecimal> getFreeMoneyByAccountId(Integer accountId){
        Map<String,BigDecimal> map = new HashMap<>();
        BigDecimal freeMoneyByAccountId = accountMapper.getFreeMoneyByAccountId(accountId);
        map.put("freemoney",freeMoneyByAccountId);
        return map;
    }
    // 账户的添加
    public Map<String,String> addAccount(Account account){
        Map<String,String> map = new HashMap<>();
        // 添加账户信息, 之前要先校验有没有该账户相同的名字
        Integer integer = accountMapper.accountExist(account);
        if(integer == 1){ // 有的话，账户存在。返回状态
            map.put("msg","该资金账户已存在");
            return map;
        }
        Integer insert = accountMapper.insert(account);
        return judgeInteger(insert,"账户插入失败","账户插入成功");
    }
    // 账户的删除
    public Map<String,String> deleteAccount(Integer accountId){ // 获取accountId
        Integer delete = accountMapper.delete(accountId);
       return judgeInteger(delete,"账户删除失败","账户删除成功");

    }
    // 账户资金修改
    public Map<String,String> updateAccount(Account account){ // 更新账户
        Integer update = accountMapper.updateMoney(account);
        return judgeInteger(update,"账户更新失败","账户更新成功");
    }

    // 通过accountId 获取流动资金、负债、投资、外借资金
    @Transactional(readOnly = true)
    public Map<String,BigDecimal> getAccountDetail(Integer accountId){
        Map<String,BigDecimal> bigDecimalMap = new HashMap<>();
        // 流动资金获取
        BigDecimal freeMoney = accountMapper.getFreeMoneyByAccountId(accountId);
        // 負債資金獲取
        BigDecimal debtPayByAccountId = debtMapper.getDebtPayByAccountId(accountId);
        // 投資資金的獲取
        BigDecimal investMoneyByAccountId = investMapper.getInvestMoneyByAccountId(accountId);
        // 外借資金
        BigDecimal borrowMoneyByAccountId = borrowMapper.getBorrowMoneyByAccountId(accountId);
        bigDecimalMap.put("liabilities",judgeNull(debtPayByAccountId));
        bigDecimalMap.put("workingCapital",judgeNull(freeMoney));
        bigDecimalMap.put("investment",judgeNull(investMoneyByAccountId));
        bigDecimalMap.put("lend",judgeNull(borrowMoneyByAccountId));
        return bigDecimalMap;
    }

    // 更新数据
    @Transactional
    public Map<String,String> updateMoney(Integer accountId1 , Integer accountId2, BigDecimal money){
        Account account  = new Account();
        // 转入账号id
        account.setAccountId(accountId2);
        account.setAccountFreeMoney(money);
        accountMapper.update(account);
        // 转出的账户id
        account.setAccountId(accountId1);
        money = money.multiply(BigDecimal.valueOf(-1));
        account.setAccountFreeMoney(money);
        accountMapper.update(account);
        return judgeInteger(1,"转账失败","转账成功");
    }

    // 获取所有流动资金大于1的账户,返回流动资金和账号id
    public Map<String,List<Account>> getSplitUserAccount(Integer userId,Integer accountId){
        List<Account> allAccount = accountMapper.getAllAccountByUserId(userId);

        allAccount = allAccount.stream().filter(account->account.getAccountId() != accountId).collect(Collectors.toList());
        Map<String,List<Account>> hashMap = new HashMap<>();
        hashMap.put("allAccount",allAccount);
        return hashMap;
    }

    // 判断是否为null
    public BigDecimal judgeNull(BigDecimal bigDecimal){
        if(Objects.equals(bigDecimal,null)){
            return BigDecimal.valueOf(0.0);
        }
        return bigDecimal;
    }
    // 通用模板
    public Map<String,String> judgeInteger(Integer integer, String not, String yes){
        Map<String,String> map = new HashMap<>();
        if(integer == 1)
        {
            map.put("msg",yes);
            return map;
        }
        map.put("msg",not);
        return  map;
    }
}
