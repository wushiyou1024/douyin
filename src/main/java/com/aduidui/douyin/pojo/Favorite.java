package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 17:22
 */
@Data
public class Favorite {
 private Long id;
 private Long video_id;
 private Long user_id;
 private String is_favorite;

}
