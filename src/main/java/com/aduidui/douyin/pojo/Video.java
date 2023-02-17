package com.aduidui.douyin.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 19:32
 */
@Data
public class Video {
    private Integer status_code;
    private String status_msg;
    private Integer next_time;
    private List<VideoListDTO> video_list;

}
