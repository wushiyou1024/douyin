package com.aduidui.douyin.pojo;

import lombok.Data;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-17 20:51
 */
@Data
public class User {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String token;
}
