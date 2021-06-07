package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.bean.User;
import com.jay.cfs_cashbook.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService; // 用户服务
    // 注册
    @PostMapping("/user")
    @ApiOperation("注册接口")
    public Map<String, String> register(@RequestBody User user)
    {
        log.info("注册用户开始" + user.toString());
        Map<String, String> register = userService.register(user);
        log.info("注册用户结束" + register.toString());
        return register;
    }
    // 登录
    @GetMapping("/user")
    @ApiOperation("登录功能")
    public Map<String,String> login(@ApiParam("用户邮箱: email") @RequestParam("email") String email,
                                    @ApiParam("用户密码：password") @RequestParam("password") String password)
    {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        log.info("登录用户开始" + email + " " + password);
        Map<String, String> login = userService.login(user);
        log.info("登录用户结束" + login.toString());
        return login;
    }
    @GetMapping("/usermsg")
    @ApiOperation("用户信息：总资金、负债、流动资金")
    public Map<String, BigDecimal> getMoneyMsg(@ApiParam("用户id:userId") @RequestParam("userId") Integer userId){
        log.info("总资金、负债、流动资金获取开始:" + userId.toString());
        Map<String, BigDecimal> homeMsg = userService.getHomeMsg(userId);
        log.info("总资金、负债、流动资金获取结束:" +homeMsg.toString());
        return homeMsg;
    }
}
