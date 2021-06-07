package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class AccountController { // 账户控制器
    @Autowired
    AccountService accountService;
    //通过userId 获取所有账户数据
    @GetMapping("/accounts")
    @ApiOperation("获取所有资金账户数据")
    public List<Account> getAccounts(@ApiParam("用户id") @RequestParam("userId") Integer userId){
        log.info("获取所有资金账户数据开始:" + userId.toString());
        List<Account> allAccount = accountService.getAllAccount(userId);
        log.info("获取所有资金账户数据结束:" + allAccount.toString());
        return allAccount;
    }
    // 通过accountId 获取所有详细的信息
    @GetMapping("/accountDetail")
    public Map<String,BigDecimal> getAccountDetail(@ApiParam("账户id") @RequestParam("accountId") Integer accountId){
        log.info("获取所有详细的信息:" + accountId.toString());
        Map<String, BigDecimal> map = accountService.getAccountDetail(accountId);
        log.info("获取所有详细的信息结束:" + map.toString());
        return map;
    }
    // 通过accountId 获取流动资金数据
    @GetMapping("/account")
    @ApiOperation("通过accountId获取流动资金数据")
    public Map<String, BigDecimal> getFreeMoney(@ApiParam("账户id") @RequestParam("accountId") Integer accountId){
        log.info("获取账户流动资金数据开始:" + accountId.toString());
        Map<String, BigDecimal> map = accountService.getFreeMoneyByAccountId(accountId);
        log.info("获取账户流动资金数据结束:" + map.toString());
        return map;
    }

    // 添加账户信息
    @PostMapping("/account")
    @ApiOperation("添加资金账户信息")
    public Map<String,String> addAccount(@ApiParam("账户所需的信息(除了accountId)")  @RequestBody Account account){
        log.info("添加资金账户信息开始: " + account.toString());
        Map<String, String> map = accountService.addAccount(account);
        log.info("添加资金账户信息结束:" + map.toString());
        return map;
    }

    // 删除账号信息
    @DeleteMapping("/account/{accountId}")
    @ApiOperation("删除账户信息")
    public Map<String,String> deleteAccount(@ApiParam("删除账户信息") @PathVariable("accountId") Integer accountId){
        log.info("删除账号信息开始:"+ accountId.toString());
        Map<String, String> map = accountService.deleteAccount(accountId);
        log.info("删除账号信息结束:"+ map.toString());
       return map;
    }

    // 更新账户
    @PutMapping("/account")
    @ApiOperation("更新账户数据")
    public Map<String,String> updateAccount(@RequestBody Account account){
        log.info("更新账户数据开始:"+ account.toString());
        Map<String, String> map = accountService.updateAccount(account);
        log.info("更新账户数据结束:"+ map.toString());
        return map;
    }
    // 排除掉当前的accountId的数据
    @GetMapping("/filterAccount/{userId}/{accountId}")
    @ApiOperation("获取过滤后的accounts数据")
    public Map<String,List<Account>> filterAccount(@ApiParam("用户Id") @PathVariable("userId") Integer userId,
                                                   @ApiParam("账户Id") @PathVariable("accountId") Integer accountId){
        log.info("排除掉当前的accountId的数据开始:"+ userId.toString());
        Map<String, List<Account>> hashmap = accountService.getSplitUserAccount(userId,accountId);
        log.info("排除掉当前的accountId的数据结束:"+ hashmap.toString());
        return hashmap;
    }
    // 账户之间资金的流动
    @GetMapping("/exchangeMoney/{accountId1}/{accountId2}")
    @ApiOperation("獲取所有")
    public Map<String,String> exchangeMoney(@ApiParam("转出的账户id")  @PathVariable String accountId1,
                                            @ApiParam("转入的账户id")  @PathVariable String accountId2,
                                            @ApiParam("金额多少")  @RequestParam("money") BigDecimal money){
        log.info("资金流动开始：accountId1 = {},accountId2 = {},money = {}" ,accountId1,accountId2,money);
        Map<String, String> map = accountService.updateMoney(Integer.valueOf(accountId1), Integer.valueOf(accountId2), money);
        log.info("资金流动结束：map = {}" ,map.toString());
        return map;
    }

}
