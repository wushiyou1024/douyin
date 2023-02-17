package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 19:35
 */
@Data
public class UserDetail {
 private static final long serialVersionUID = 1L;

 private Long id;
 private String name;
 private Integer follow_count;
 private  Integer follower_count;
 private String is_follow;
 private String avatar;
 private String background_image;
 private String signature;
 private String total_favorited;
 private Integer  work_count;
 private Integer favorite_count;

}
