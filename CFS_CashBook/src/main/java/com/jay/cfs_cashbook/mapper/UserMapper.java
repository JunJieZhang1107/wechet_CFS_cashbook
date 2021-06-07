package com.jay.cfs_cashbook.mapper;


import com.jay.cfs_cashbook.bean.User;
import io.swagger.models.auth.In;

public interface UserMapper {
    // 检验邮箱是否存在
    public Integer userExistByEmail(String email);
    // 插入数据
    public Integer insert(User user);
    // 通过email查找所有数据
    public User getUserByEmail(String email);
}
