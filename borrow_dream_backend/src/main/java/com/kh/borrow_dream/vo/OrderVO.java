package com.kh.borrow_dream.vo;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO {
    private long od_num;    
    private int od_pnum;    
    private String od_pname;
    private int od_price;
    private Date od_date;
    private Date borrow1;
    private Date borrow2;
    private int od_dayCnt;
    private int od_point;
    private int od_tPrice;
    private int od_quantity;
    private String id;
}
