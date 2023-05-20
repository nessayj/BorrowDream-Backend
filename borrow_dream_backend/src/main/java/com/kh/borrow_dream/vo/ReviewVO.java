package com.kh.borrow_dream.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReviewVO {
    private int reviewNo;
    private String rTitle;
    private String rContents;
    private String rID;
    private String rUrl;
    private int youLike;
    private Date rDate;
}
