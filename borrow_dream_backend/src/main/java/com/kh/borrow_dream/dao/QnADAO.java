package com.kh.borrow_dream.dao;


import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.QnAVO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository

public class QnADAO {
    private Connection conn =null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;


    // QnA 조회
    public List<QnAVO> getAllQna() {
        List<QnAVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql="SELECT * FROM QNA ORDER BY QNA_NO";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int qnaNO = rs.getInt("QNA_NO");
                String qTitle = rs.getString("Q_TITLE");
                String qContents = rs.getString("Q_CONTENTS");

                QnAVO vo = new QnAVO();
                vo.setQnaNo(qnaNO);
                vo.setQTitle(qTitle);
                vo.setQContents(qContents);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
       return list;
    }


}
