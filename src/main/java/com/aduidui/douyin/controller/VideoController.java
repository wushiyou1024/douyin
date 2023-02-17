package com.aduidui.douyin.controller;

import com.aduidui.douyin.pojo.UserDetail;
import com.aduidui.douyin.pojo.Video;
import com.aduidui.douyin.pojo.VideoList;
import com.aduidui.douyin.pojo.VideoListDTO;
import com.aduidui.douyin.service.UserDetailService;
import com.aduidui.douyin.service.VideoListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private UserDetailService userDetailService;

    @GetMapping("/feed")
    public Video feed() {
        Video video = new Video();
        video.setStatus_code(0);
        video.setStatus_msg("这是测试");
        video.setNext_time(null);
        List<VideoList> videoLists = videoListService.list();
        List<VideoListDTO> videoListDTOS = videoLists.stream().map(
                (item) -> {
                    VideoListDTO videoListDTO = new VideoListDTO();
                    BeanUtils.copyProperties(item, videoListDTO, "author");
                    UserDetail userDetail = userDetailService.getById(item.getAuthor_id());
                    videoListDTO.setAuthor(userDetail);
                    return videoListDTO;
                }
        ).collect(Collectors.toList());


        video.setVideo_list(videoListDTOS);
        return video;
    }
}
