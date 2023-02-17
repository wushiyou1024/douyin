package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 21:55
 */
@Data
public class UserMsg {
    private Integer status_code;
    private String status_msg;
    private Long user_id;
    private String token;
}
