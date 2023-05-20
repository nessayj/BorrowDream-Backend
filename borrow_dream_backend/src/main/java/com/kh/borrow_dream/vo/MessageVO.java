package com.kh.borrow_dream.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageVO {
    private int msgNo;
    private String sender;
    private String receiver;
    private String msgTitle;
    private String msgContents;
    private Date msgDate;

}
