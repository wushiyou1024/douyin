package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 22:14
 */
@Data
public class DetailMsg {
    private Integer status_code;
    private String status_msg;
    private UserDetail user;
}
