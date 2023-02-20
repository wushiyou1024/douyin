package com.aduidui.douyin.service.impl;

import com.aduidui.douyin.mapper.CommentMapper;
import com.aduidui.douyin.pojo.Comment;
import com.aduidui.douyin.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 20:26
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
