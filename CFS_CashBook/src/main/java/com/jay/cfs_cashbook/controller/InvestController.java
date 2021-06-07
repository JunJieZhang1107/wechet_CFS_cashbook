package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.bean.Invest;
import com.jay.cfs_cashbook.mapper.InvestMapper;
import com.jay.cfs_cashbook.service.InvestService;
import com.jay.cfs_cashbook.views.InvestView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class InvestController {
    // 获取模糊数据
    @GetMapping("/invest")
    public List<Invest> index(@RequestParam("userId") Integer userId){
        List<Invest> allInvest = investService.getAllInvest(userId);
        return allInvest;
    }
    // 获取详细数据
    @GetMapping("/investDetail/{accountId}/{investId}")
    public InvestView detail(@PathVariable("investId") int investId,
                             @PathVariable("accountId") int accountId){
        InvestView detailMsg = investService.getDetailMsg(accountId, investId);
        return detailMsg;
    }
    // 更新页面数据
    @PutMapping("/invest")
    public Map<String,String> update(@RequestBody Invest invest){
        log.info(invest.toString());
        Map<String, String> update = investService.update(invest);
        return update;
    }

    @PostMapping("/invest")
    public Map<String,String> add(@RequestBody Invest invest){
        Map<String, String> insert = investService.insert(invest);
        return insert;
    }

    @Autowired
    InvestService investService;
}
