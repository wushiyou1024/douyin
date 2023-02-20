package com.aduidui.douyin.controller;


import com.aduidui.douyin.pojo.*;
import com.aduidui.douyin.service.UserDetailService;
import com.aduidui.douyin.service.UserService;
import com.aduidui.douyin.service.VideoListService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Bless_Wu
 * @Description 文件上传保存到本地
 * @create 2022-10-29 20:19
 */
@RestController
@RequestMapping("/douyin")
@Slf4j
@CrossOrigin
public class CommonController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailService detailService;
    @Autowired
    private VideoListService videoListService;

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/publish/action/")
    public OK upload(MultipartFile data, String title, String token) {

        //通过token获取用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(token != null, User::getToken, token);
        User user = userService.getOne(queryWrapper);
        //通过用户更新作品数+1
        LambdaQueryWrapper<UserDetail> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(UserDetail::getUser_id, user.getId());
        UserDetail userDetail = detailService.getOne(queryWrapper1);
        ;
        if (userDetail != null) {
            userDetail.setWork_count(userDetail.getWork_count() + 1);
            detailService.updateById(userDetail);
        }
        System.out.println(title + token);
        //原始文件名
        String filename = data.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));

        File dir = new File(basePath);
        //不存在目录需要创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //使用uuid重新生成文件名
        filename = UUID.randomUUID().toString() + suffix;//suffix为.jpg 拼接uuid
        //file是一个临时文件，需要转存到指定位置
        //将作品保存到视频列表中
        VideoList videoList = new VideoList();
        videoList.setCover_url("http://192.168.123.184:8080/img/iu.jpeg");
        videoList.setTitle(title);
        videoList.setComment_count(0);
        videoList.setFavorite_count(0);
        videoList.setPlay_url(filename);
        videoList.setIs_favorite("false");
        videoList.setAuthor_id(userDetail.getId());
        videoListService.save(videoList);

        OK ok = new OK();
        try {
            data.transferTo(new File(basePath + filename));
        } catch (Exception e) {
            e.printStackTrace();
            ok.setStatus_msg("文件太大啦！！！！！！！！！");
            ok.setStatus_code(-1);
            return ok;
        }

        ok.setStatus_code(0);
        return ok;
    }

    @GetMapping("/publish/list/")
    public Video getVideoByUser(String token, String user_id) {
        UserDetail userDetail = detailService.getById(user_id);
        Video video = new Video();
        video.setStatus_code(0);
        video.setStatus_msg("这是测试");
        video.setNext_time(null);
        List<VideoListDTO> videoListDTOS = null;

        //根据详细id获取所有的视频
        LambdaQueryWrapper<VideoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(VideoList::getAuthor_id, userDetail.getId());
        List<VideoList> videoLists = videoListService.list(queryWrapper);
        videoListDTOS = videoLists.stream().map(
                (item) -> {
                    VideoListDTO videoListDTO = new VideoListDTO();
                    BeanUtils.copyProperties(item, videoListDTO, "author");
                    UserDetail tmpuser = detailService.getById(item.getAuthor_id());
                    videoListDTO.setPlay_url("http://192.168.123.184:8080/douyin/download?name=" + videoListDTO.getPlay_url());
                    videoListDTO.setAuthor(tmpuser);
                    return videoListDTO;
                }
        ).collect(Collectors.toList());


        video.setVideo_list(videoListDTOS);
        return video;
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        //输入流 读取文件
        ServletOutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流 把文件写回到浏览器
            outputStream = response.getOutputStream();

            response.setHeader("Content-Type", "video/mpeg4");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
