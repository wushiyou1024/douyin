package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 20:24
 */
@Data
public class Comment {
 private Long id;
 private Long user_id;
 private Long video_id;
 private String content;
 private String create_date;

}
