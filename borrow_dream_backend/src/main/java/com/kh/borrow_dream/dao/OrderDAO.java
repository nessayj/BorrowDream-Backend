package com.kh.borrow_dream.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.OrderVO;

public class OrderDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    public List<OrderVO> orderList(String id){
        List<OrderVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 주문내역 WHERE OD_CUS = '" + id + "' ORDER BY OD_DATE";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                long no = rs.getLong("OD_NUM");
                String name = rs.getString("OD_NAME");
                int price = rs.getInt("OD_PRICE");
                int quantity = rs.getInt("OD_QU");
                Date date = rs.getDate("OD_DATE");
                Date borrow1 = rs.getDate("OD_BORROW");
                Date borrow2 = rs.getDate("OD_RETURN");
                int dayCnt = rs.getInt("OD_TOTALDAY");
                int point = rs.getInt("OD_POINT");
                int total = rs.getInt("OD_TOTAL");

                OrderVO vo = new OrderVO();
                vo.setOd_num(no);
                vo.setOd_pname(name);
                vo.setOd_price(price);
                vo.setOd_date(date);
                vo.setBorrow1(borrow1);
                vo.setBorrow2(borrow2);
                vo.setOd_dayCnt(dayCnt);
                vo.setOd_quantity(quantity);
                vo.setOd_tPrice(total);
                vo.setOd_point(point);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean orderUpdate(String id) {
        int result = 0;
        String sql = "INSERT INTO 주문내역 (OD_NUM, OD_AT_NUM, OD_NAME, OD_PRICE, OD_DATE, OD_BORROW, OD_RETURN, OD_TOTALDAY, OD_POINT, OD_TOTAL, OD_QU, OD_CUS) "
                    + "SELECT (TO_CHAR(SYSTIMESTAMP, 'YYMMDDHH24MISS')+주문번호_SEQ.NEXTVAL), BK_AT_NUM, BK_NAME, BK_PRICE, SYSDATE, BK_BORROW, BK_RETURN, BK_TOTALDAY, (BK_PRICE * BK_QU * BK_TOTALDAY / 100),"
                    + " (BK_PRICE * BK_QU * BK_TOTALDAY), BK_QU, BK_CUS FROM 장바구니 WHERE BK_CUS = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
    
            result = pStmt.executeUpdate();
            System.out.println("리스트 추가 DB 결과 확인 : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    
        if (result == 1) return true;
        else return false;
    }
}