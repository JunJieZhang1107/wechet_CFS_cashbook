package com.jay.cfs_cashbook.service;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.Budget;
import com.jay.cfs_cashbook.bean.IncomeOutgo;
import com.jay.cfs_cashbook.forms.CreateExpend;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.BudgetMapper;
import com.jay.cfs_cashbook.mapper.IncomeOutgoMapper;
import com.jay.cfs_cashbook.views.IncomeOutgoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class IncomeOutgoService {
    // 保存数据
    @Transactional
    public Map<String,String> save(IncomeOutgo incomeOutgo) {
        incomeOutgo.setTime(new Date());
        Integer save = incomeOutgoMapper.save(incomeOutgo);
        BigDecimal bigDecimal = incomeOutgo.getMoney();

        if(incomeOutgo.getType()){ // true则为收入
            accountMapper.updateDelteIncomeOutgo(incomeOutgo.getAccountId(),bigDecimal);
        }else{ // false则为
            bigDecimal=bigDecimal.multiply(BigDecimal.valueOf(-1));
            accountMapper.updateDelteIncomeOutgo(incomeOutgo.getAccountId(),bigDecimal);
        }
        Map<String, String> map = accountService.judgeInteger(save, "插入失败", "插入成功");
        return map;
    }

    // 获取数据
    public List<IncomeOutgoView> getData(Integer userId){
        List<IncomeOutgo> allByUserId = incomeOutgoMapper.getAllByUserId(userId);
        List<IncomeOutgoView> views = new ArrayList<>();

        for(IncomeOutgo item : allByUserId){
            IncomeOutgoView incomeOutgoView = new IncomeOutgoView();
            Account account = accountMapper.getAccountNameByAccountId(item.getAccountId());
            incomeOutgoView.setIncomeOutgo(item);
            incomeOutgoView.setAccount(account);
            views.add(incomeOutgoView);
        }
        return views;
    }

    // 删除数据,回退金额
    @Transactional
    public Map<String,String> destroy(IncomeOutgo incomeOutgo) {
        Map<String,String> hashmap = new HashMap<>();
        BigDecimal bigDecimal = incomeOutgo.getMoney();
        if(incomeOutgo.getType()){ // true则为收入
            bigDecimal=bigDecimal.multiply(BigDecimal.valueOf(-1));
            accountMapper.updateDelteIncomeOutgo(incomeOutgo.getAccountId(),bigDecimal);
        }else{ // false则为
            accountMapper.updateDelteIncomeOutgo(incomeOutgo.getAccountId(),bigDecimal);
        }
        Integer delete = incomeOutgoMapper.delete(incomeOutgo);
        Map<String, String> map = accountService.judgeInteger(delete, "删除失败", "删除成功");
        return map;
    }
    @Transactional
    public Map<String, String> expend(CreateExpend createExpend) {
        Map<String,String> map = new HashMap<>();
        BigDecimal spendMoney = createExpend.getSpendMoney();
        spendMoney = spendMoney.multiply(BigDecimal.valueOf(-1));
        accountMapper.updateDelteIncomeOutgo(createExpend.getAccountId(),spendMoney);
        // 扣除相应的预算
        budgetMapper.updatePartMoney(trancToBudgetType(createExpend));
        // 保存记录在收入支出表中。
        incomeOutgoMapper.save(trancToIncomeOutgoType(createExpend));
        map.put("msg","开支成功");
        return map;
    }
    private Budget trancToBudgetType(CreateExpend it){
        Budget budget = new Budget();
        switch (it.getBudgetType()){
            case 0: budget.setEat(it.getSpendMoney().multiply(BigDecimal.valueOf(-1))); break;
            case 1: budget.setDrink(it.getSpendMoney().multiply(BigDecimal.valueOf(-1))); break;
            case 2: budget.setPlay(it.getSpendMoney().multiply(BigDecimal.valueOf(-1))); break;
            case 3: budget.setFunny(it.getSpendMoney().multiply(BigDecimal.valueOf(-1))); break;
            case 4: budget.setImprove(it.getSpendMoney().multiply(BigDecimal.valueOf(-1))); break;
            default: break;
        }
        budget.setId(it.getBudgetId());
        return budget;
    }
    private IncomeOutgo trancToIncomeOutgoType(CreateExpend it){
        IncomeOutgo tranc = new IncomeOutgo();
        tranc.setAccountId(it.getAccountId());
        tranc.setType(false);
        tranc.setMoney(it.getSpendMoney());
        tranc.setReason(it.getReason());
        tranc.setTime(new Date());
        return  tranc;
    }

    @Autowired
    IncomeOutgoMapper incomeOutgoMapper;
    @Autowired
    BudgetMapper budgetMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountMapper accountMapper;

}
