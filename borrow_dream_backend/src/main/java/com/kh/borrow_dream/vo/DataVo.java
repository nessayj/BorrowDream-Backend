package com.kh.borrow_dream.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DataVo {
//    private int id;
    private Date startDate;
    private Date endDate;
    private int totalDays;
}
