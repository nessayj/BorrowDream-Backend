package com.kh.borrow_dream.controller;

import com.kh.borrow_dream.dao.DataDAO;
import com.kh.borrow_dream.vo.DataVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CalendarController {

    private final DataDAO dataDAO;

    // 날짜 범위 저장 API
    @PostMapping("/api/save-date-range")
    public boolean insertData(@RequestBody DataVo dataVo) {

        try {
            log.info("data={}", dataVo.getTotalDays());

            // 데이터베이스에 날짜 범위 데이터 저장
            dataDAO.insertData(dataVo.getStartDate(), dataVo.getEndDate(), dataVo.getTotalDays());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
