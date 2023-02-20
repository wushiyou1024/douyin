package com.aduidui.douyin.controller;

import com.aduidui.douyin.pojo.*;
import com.aduidui.douyin.service.FavoriteService;
import com.aduidui.douyin.service.UserService;
import com.aduidui.douyin.service.VideoListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.prism.impl.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 17:25
 */
@RestController()
@RequestMapping("/douyin")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private VideoListService videoListService;
    @Autowired
    private UserService userService;

    @PostMapping("/favorite/action/")
    public OK favoriteAction(String token, String video_id, String action_type) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getToken, token);
        User user = userService.getOne(userLambdaQueryWrapper);

        //新增favorite记录
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getVideo_id, video_id);
        queryWrapper.eq(Favorite::getUser_id,user.getId());
        Favorite favorite = favoriteService.getOne(queryWrapper);
        VideoList videoList = videoListService.getById(video_id);

        Integer favorite_count = videoList.getFavorite_count();
        if (action_type.equals("1")) {

            videoList.setFavorite_count(favorite_count + 1);
        } else if (action_type.equals("2")) {

            videoList.setFavorite_count(favorite_count - 1);
        }
        videoListService.updateById(videoList);
        if (favorite == null) {
            //新增
            Favorite newFavorite = new Favorite();
            newFavorite.setIs_favorite(action_type);
            newFavorite.setUser_id(user.getId());
            newFavorite.setVideo_id(Long.parseLong(video_id));
            favoriteService.save(newFavorite);
        } else {
            //修改
            favorite.setIs_favorite(action_type);
            favoriteService.updateById(favorite);
        }
        //2.修改videolist的操作。

        OK ok = new OK();
        ok.setStatus_code(0);
        return ok;
    }

    @GetMapping("/favorite/list/")
    public Video getAllFavorite(String user_id, String token) {
        System.out.println("favorite被调用");
        List<VideoListDTO> videoList = null;
        Video video = new Video();
        video.setStatus_code(0);
        video.setStatus_msg("这是测试");
        video.setNext_time(null);
        List<VideoList> videoLists = videoListService.list();
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getToken,token);
        User user = userService.getOne(queryWrapper);
        if (user_id == null || token == null || user_id.equals("") || token.equals("")) {
            videoList = videoListService.getVideoList(videoLists, null);
        }else {

            videoList = videoListService.getVideoList(videoLists, user.getId().toString());
        }
        video.setVideo_list(videoList);
        return video;
    }
}
