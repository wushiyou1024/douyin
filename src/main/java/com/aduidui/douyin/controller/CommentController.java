package com.aduidui.douyin.controller;

import com.aduidui.douyin.pojo.*;
import com.aduidui.douyin.service.CommentService;
import com.aduidui.douyin.service.UserDetailService;
import com.aduidui.douyin.service.UserService;
import com.aduidui.douyin.service.VideoListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 20:27
 */
@RestController
@RequestMapping("/douyin")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService detailService;
    @Autowired
    private VideoListService videoListService;

    @PostMapping("/comment/action/")
    public CommentOK comment(String token, String video_id, String action_type, String comment_text, String comment_id) {
        System.out.println("1");
        CommentDTO commentDTO = new CommentDTO();
        CommentOK ok = new CommentOK();
        if (action_type.equals("1")) {
            Comment comment = new Comment();
            comment.setContent(comment_text);
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getToken, token);
            User user = userService.getOne(queryWrapper);
            comment.setUser_id(user.getId());
            comment.setVideo_id(Long.parseLong(video_id));
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String format = sdf.format(new Date());
            comment.setCreate_date(format);
            commentService.save(comment);

            commentDTO.setContent(comment_text);
            commentDTO.setId(comment.getId());
            commentDTO.setCreate_date(format);
            LambdaQueryWrapper<UserDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(UserDetail::getUser_id, user.getId());
            UserDetail userDetail = detailService.getOne(detailLambdaQueryWrapper);
            commentDTO.setUser(userDetail);
            ok.setComment(commentDTO);

        } else if (action_type.equals("2")) {
            commentService.removeById(comment_id);
            ok.setComment(null);
        }
        ok.setStatus_code(0);
        return ok;
    }

    @GetMapping("/comment/list/")
    public OK getCommentList(String token, String video_id) {
        System.out.println("4");

        CommentOK ok = new CommentOK();
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getVideo_id, video_id);
        List<Comment> list = commentService.list(queryWrapper);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : list) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setCreate_date(comment.getCreate_date());
            UserDetail userDetail = detailService.getById(comment.getUser_id());
            commentDTO.setUser(userDetail);
            commentDTOList.add(commentDTO);
        }

        ok.setComment_list(commentDTOList);
        ok.setStatus_code(0);
        return ok;
    }
}
