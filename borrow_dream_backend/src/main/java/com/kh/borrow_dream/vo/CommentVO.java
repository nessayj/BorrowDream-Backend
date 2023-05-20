package com.kh.borrow_dream.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentVO {
    private int commentNo;
    private int boardNo;
    private String commentId;
    private String contents;
    private Date commentTime;

}
