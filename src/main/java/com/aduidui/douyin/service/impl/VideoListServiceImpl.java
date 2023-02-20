package com.aduidui.douyin.service.impl;

import com.aduidui.douyin.mapper.VideoListMapper;
import com.aduidui.douyin.pojo.Favorite;
import com.aduidui.douyin.pojo.UserDetail;
import com.aduidui.douyin.pojo.VideoList;
import com.aduidui.douyin.pojo.VideoListDTO;
import com.aduidui.douyin.service.FavoriteService;
import com.aduidui.douyin.service.UserDetailService;
import com.aduidui.douyin.service.VideoListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 20:57
 */
@Service
public class VideoListServiceImpl extends ServiceImpl<VideoListMapper, VideoList> implements VideoListService {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private FavoriteService favoriteService;
    @Override
    public List<VideoListDTO> getVideoList(List<VideoList> videoLists, String user_id) {
        List<VideoListDTO> videoListDTOS = videoLists.stream().map(
                (item) -> {
                    VideoListDTO videoListDTO = new VideoListDTO();
                    BeanUtils.copyProperties(item, videoListDTO, "author");
                    UserDetail userDetail = userDetailService.getById(item.getAuthor_id());
                    videoListDTO.setPlay_url("http://192.168.123.184:8080/douyin/download?name=" + videoListDTO.getPlay_url());
                    videoListDTO.setAuthor(userDetail);
                   if (user_id!=null){

                       LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper=new LambdaQueryWrapper<>();
                       favoriteLambdaQueryWrapper.eq(Favorite::getUser_id,user_id);
                       favoriteLambdaQueryWrapper.eq(Favorite::getVideo_id,videoListDTO.getId());
                       Favorite favorite = favoriteService.getOne(favoriteLambdaQueryWrapper);
                       if (favorite != null) {
                           if (favorite.getIs_favorite().equals("1")) {
                               videoListDTO.setIs_favorite("true");
                           } else if (favorite.getIs_favorite().equals("2")){
                               videoListDTO.setIs_favorite("false");
                           }
                       }
                   }
                    return videoListDTO;
                }
        ).collect(Collectors.toList());
        return videoListDTOS;
    }
}
