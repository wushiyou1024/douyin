package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 19:33
 */
@Data
public class VideoList{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String play_url;
    private String cover_url;
    private Integer favorite_count;
    private Integer comment_count;
    private String is_favorite;
    private String title;
    private Long author_id;

}
