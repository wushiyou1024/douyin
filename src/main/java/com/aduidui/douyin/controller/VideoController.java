package com.aduidui.douyin.controller;

import com.aduidui.douyin.pojo.*;
import com.aduidui.douyin.service.FavoriteService;
import com.aduidui.douyin.service.UserDetailService;
import com.aduidui.douyin.service.UserService;
import com.aduidui.douyin.service.VideoListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.prism.impl.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 19:38
 */
@RestController
@RequestMapping("/douyin")
public class VideoController {

    @Autowired
    private VideoListService videoListService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/feed")
    public Video feed(String token, String latest_time) {
        System.out.println(token);
        Video video = new Video();
        video.setStatus_code(0);
        video.setStatus_msg("这是测试");
        video.setNext_time(null);
        List<VideoList> videoLists = videoListService.list();
        List<VideoListDTO> videoListDTOS = null;
        if (token == null || token.equals("")) {
            videoListDTOS = videoListService.getVideoList(videoLists, null);
        }else {
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getToken,token);
            User user = userService.getOne(queryWrapper);
            videoListDTOS = videoListService.getVideoList(videoLists, user.getId().toString());
        }
        video.setVideo_list(videoListDTOS);
        return video;
    }
}
