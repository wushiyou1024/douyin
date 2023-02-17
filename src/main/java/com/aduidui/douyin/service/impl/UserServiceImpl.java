package com.aduidui.douyin.service.impl;

import com.aduidui.douyin.mapper.UserMapper;
import com.aduidui.douyin.pojo.User;
import com.aduidui.douyin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 20:54
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
