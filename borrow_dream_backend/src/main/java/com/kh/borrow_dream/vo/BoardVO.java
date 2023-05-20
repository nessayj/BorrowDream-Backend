package com.kh.borrow_dream.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BoardVO {
    private int boardNo;
    private String category;
    private String title;
    private String writerId;
    private int views;
    private Date writeDate;
    private String contents;
    private int boardPwd;
    private int isUnknown;
}
