package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 20:34
 */
@Data
public class CommentDTO {
    private Long  id;
    private UserDetail user;
    private String content;
    private String create_date;
}
