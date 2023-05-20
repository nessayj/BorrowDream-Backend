package com.kh.borrow_dream.dao;

import com.kh.borrow_dream.common.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Slf4j
public class DataDAO {
    private Connection connection;

    // 데이터 저장 메서드
    public void insertData(Date startDate, Date endDate, int totalDays) throws SQLException {
        connection = Common.getConnection();
        log.info("connection={}", connection);

        // SQL 쿼리문
        String sql = "INSERT INTO calendar_data (start_date, end_date, total) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // PreparedStatement를 사용하여 쿼리에 파라미터 설정
            statement.setDate(1, startDate);
            statement.setDate(2, endDate);
            statement.setInt(3, totalDays);

            // 쿼리 실행
            statement.executeUpdate();
        }
    }
}
