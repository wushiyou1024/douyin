package com.aduidui.douyin.controller;

import com.aduidui.douyin.pojo.DetailMsg;
import com.aduidui.douyin.pojo.User;
import com.aduidui.douyin.pojo.UserDetail;
import com.aduidui.douyin.pojo.UserMsg;
import com.aduidui.douyin.service.UserDetailService;
import com.aduidui.douyin.service.UserService;
import com.alibaba.druid.sql.visitor.functions.Now;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-14 16:00
 */
@RestController()
@RequestMapping("/douyin")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService detailService;

    @PostMapping("/user/register/")
    public UserMsg register(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(username != null, User::getUsername, username);
        User one = userService.getOne(queryWrapper);
        UserMsg userMsg = new UserMsg();
        if (one != null) {
            userMsg.setStatus_code(-1);
            return userMsg;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 1、普通的时间转换
        String string = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();

        String token = username + string;
        user.setToken(token);
        userService.save(user);

        UserDetail userDetail = new UserDetail();
        userDetail.setWork_count(0);
        userDetail.setFavorite_count(0);
        userDetail.setAvatar("http://192.168.123.184:8080/img/iu.jpeg");
        userDetail.setBackground_image("http://192.168.123.184:8080/img/iu.jpeg");
        userDetail.setFollow_count(0);
        userDetail.setFollower_count(0);
        userDetail.setName("新用户");
        userDetail.setSignature("谢谢你的关注");
        userDetail.setTotal_favorited(null);
        userDetail.setUser_id(user.getId());
        detailService.save(userDetail);
        userMsg.setStatus_code(0);
        userMsg.setStatus_msg(null);
        userMsg.setUser_id(user.getId());
        userMsg.setToken(token);
        return userMsg;
    }

    @PostMapping("/user/login/")
    public UserMsg login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(username != null, User::getUsername, username);
        queryWrapper.eq(password != null, User::getPassword, password);
        User user = userService.getOne(queryWrapper);
        UserMsg userMsg = new UserMsg();
        if (null == user) {
            userMsg.setStatus_code(-1);
            return userMsg;
        }
        userMsg.setUser_id(user.getId());
        userMsg.setToken(user.getToken());
        userMsg.setStatus_code(0);
        return userMsg;
    }

    @GetMapping("/user/")
    public DetailMsg getDetail(String user_id, String token) {
        User user = userService.getById(user_id);
        DetailMsg detailMsg = new DetailMsg();
        if (user.getToken().equals(token)) {
            detailMsg.setStatus_code(0);
            LambdaQueryWrapper<UserDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserDetail::getUser_id, user_id);
            UserDetail detail = detailService.getOne(queryWrapper);
            detailMsg.setUser(detail);
        }


        return detailMsg;
    }
}
