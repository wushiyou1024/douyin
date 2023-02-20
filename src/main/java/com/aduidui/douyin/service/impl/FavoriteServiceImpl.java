package com.aduidui.douyin.service.impl;

import com.aduidui.douyin.mapper.FavoriteMapper;
import com.aduidui.douyin.pojo.Favorite;
import com.aduidui.douyin.pojo.UserDetail;
import com.aduidui.douyin.pojo.VideoList;
import com.aduidui.douyin.pojo.VideoListDTO;
import com.aduidui.douyin.service.FavoriteService;
import com.aduidui.douyin.service.UserDetailService;
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
 * @create 2023-02-20 17:28
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper,Favorite> implements FavoriteService {

}
