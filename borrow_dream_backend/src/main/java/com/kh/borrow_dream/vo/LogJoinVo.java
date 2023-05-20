package com.kh.borrow_dream.vo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class LogJoinVo {
    private String name; // value object : 정보전달용(프엔 기준 요청 기준 : 디비테이블 컬럼 갯수와 꼭 맞출 필요없음)
    private String id;
    private String pwd;
    private String tel;
    private String email;
    private String addr;
    private Date join;
}
