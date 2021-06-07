package com.jay.cfs_cashbook.service;

import com.alibaba.fastjson.JSON;
import com.jay.cfs_cashbook.bean.User;
import com.jay.cfs_cashbook.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    AccountMapper accountMapper; // 子账户信息
    @Autowired
    BorrowMapper borrowMapper; // 外借资金
    @Autowired
    InvestMapper investMapper; // 投资模块
    @Autowired
    DebtMapper debtMapper; // 负债模块

    // 注册
    public Map<String,String> register(User user)
    {
        Map<String,String> map = new HashMap<>();
        // 检验该邮箱是否存在,当邮箱存在, 注册失败。 当邮箱不存在，注册成功
        String email = user.getEmail();
        String password = user.getPassword();
        Integer integer = userMapper.userExistByEmail(email);
        // 判断是否为1
        if(integer == 1)
        {
            map.put("msg","邮箱存在");
            return map;
        }
        // 插入数据
        Integer insert = userMapper.insert(user);
        if(insert != 1){// 插入失败
            map.put("msg","注册失败");
        }
        map.put("msg","注册成功,返回用户数据");
        String s = JSON.toJSONString(user);
        map.put("user",s);
        return map;
    }
    // 登录
    @Transactional // 加入事务
    public Map<String,String> login(User user){
        Map<String,String> map = new HashMap<>();
        String email = user.getEmail();
        String password = user.getPassword();
        Integer integer = userMapper.userExistByEmail(email);
        if(integer != 1){
            map.put("msg","用户不存在");
            return map;
        }
        // 通过email获取用户信息
        User userByEmail = userMapper.getUserByEmail(email);
        if(password.equals(userByEmail.getPassword())){
            map.put("msg","登录成功");
            String s = JSON.toJSONString(userByEmail);
            map.put("data",s);
            return  map;
        }
        map.put("msg","登录失败");
        return map;
    }
    // 获取总金额、负债、流动资金
    @Transactional
    public Map<String,BigDecimal> getHomeMsg (Integer userId) {
        // 获取流动资金、总资产、总负债， 需要对三个表获取对应的数据（未写情况)
        Map<String,BigDecimal> map = new HashMap<>();
        // 获取所有资金账户的账户id
        List<Integer> countIdList = accountMapper.getAllCountIdByUserId(userId);
        if(countIdList.size() == 0){
            map.put("msg",BigDecimal.valueOf(-1));
            return map;
        }
        // 流动资金
        BigDecimal freeMoney =  accountMapper.getAllFreeMoneyByUserId(userId).setScale(2,BigDecimal.ROUND_DOWN);
        // 所有外借资金
        BigDecimal borrowMoney = borrowMapper.getBorrowMoneyByAccountIdList(countIdList).setScale(2,BigDecimal.ROUND_DOWN);
        BigDecimal totalMoney = borrowMoney.add(freeMoney);
        // 所有投资资金
        BigDecimal investMoney = investMapper.getAllInvestMoneyByAccountList(countIdList);
        // 所有负债资金
        BigDecimal debtMoney = debtMapper.getDebtByAccountList(countIdList);
        // 总资金 = 流动 + 外借 + 投资
        totalMoney = totalMoney.add(investMoney);

        map.put("freemoney",freeMoney); // 流动资金
        map.put("totalmoney",totalMoney); // 总金额
        map.put("debtmoney", debtMoney); // 负债金额
        return map;
    }
}
