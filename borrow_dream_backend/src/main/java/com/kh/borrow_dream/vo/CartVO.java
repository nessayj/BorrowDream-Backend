package com.kh.borrow_dream.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CartVO {
    private int bk_bno;
    private int bk_pno;
    private String bk_pname;
    private int bk_price;
    private Date bk_date;
    private Date borrow1;
    private Date borrow2;
    private int dayCnt;
    private int quantity;
    private int tPrice;
    private String id;
}
