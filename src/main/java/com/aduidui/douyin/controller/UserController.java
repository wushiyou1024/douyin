package com.aduidui.douyin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-14 16:00
 */
@RestController()
@RequestMapping("/douyin")
public class UserController {
    @PostMapping("/user/register/")
    public void register(String username,String password)
    {
        System.out.println(username);
        System.out.println(password);
    }
}
