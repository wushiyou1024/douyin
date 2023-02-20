package com.aduidui.douyin.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author Bless_Wu
 * @Description
 * @create 2023-02-20 20:33
 */
@Data
public class CommentOK extends OK{
private CommentDTO comment;
private List<CommentDTO> comment_list;
}
