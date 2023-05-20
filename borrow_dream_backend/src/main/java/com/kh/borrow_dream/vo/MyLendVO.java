package com.kh.borrow_dream.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MyLendVO {
    private int myNo;
    private String borrowId;
    private int category;
    private String myItem;
    private String itemExplain;
    private String itemUrl;
    private int itemQuantity;
    private int itemPrice;
    private Date myDate;
    private int isBorrowed;


}
