package com.kh.borrow_dream.dao;


import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.MyLendVO;
import org.springframework.stereotype.Repository;

import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class MyLendDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 빌려준품목들 내역
    public List<MyLendVO> getAllMyLend() {
        List<MyLendVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 내빌드 ORDER BY MY_DATE DESC";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int myNo = rs.getInt("MY_NO");
                String myItem = rs.getString("MY_ITEM");
                String itemExplain = rs.getString("ITEM_EXPLAIN");
                String itemUrl = rs.getString("ITEM_URL");
                int itemQuantity = rs.getInt("ITEM_QUANTITY");
                int itemPrice = rs.getInt("ITEM_PRICE");
                String borrowId = rs.getString("ID");
                Date myDate = rs.getDate("MY_DATE");
                int isBorrowed = rs.getInt("IS_BORROWED");
                int category = rs.getInt("CATEGORY");


                MyLendVO vo = new MyLendVO();
                vo.setMyNo(myNo);
                vo.setMyItem(myItem);
                vo.setItemExplain(itemExplain);
                vo.setItemUrl(itemUrl);
                vo.setItemQuantity(itemQuantity);
                vo.setItemPrice(itemPrice);
                vo.setBorrowId(borrowId);
                vo.setMyDate(myDate);
                vo.setIsBorrowed(isBorrowed);
                vo.setCategory(category);
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

    // 내빌드 상품등록
    public boolean writeMyLend(String borrowId, String myItem,  String itemExplain, String itemUrl, int itemQuantity, int itemPrice) {
        int rs = 0;
        String sql = "INSERT INTO 내빌드 VALUES (seq_MYLEND.NEXTVAL, ?, 5000, ?, ?, ?, ?, ?, SYSDATE,0)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, borrowId);
            pStmt.setString(2, myItem);
            pStmt.setString(3, itemExplain);
            pStmt.setString(4, itemUrl);
            pStmt.setInt(5, itemQuantity);
            pStmt.setInt(6, itemPrice);
            rs = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(rs == 1) return true;
        else return false;
    }

    // 상품 글 상세보기
    public MyLendVO viewMyLend(int myNo) {
        MyLendVO vo = null;
        try {
            conn = Common.getConnection();
            String sql = "SELECT ID, MY_ITEM, ITEM_EXPLAIN, ITEM_URL, ITEM_QUANTITY, ITEM_PRICE, MY_DATE, IS_BORROWED FROM 내빌드 WHERE MY_NO = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, myNo);
            rs = pStmt.executeQuery();

            if(rs.next()) {
                vo = new MyLendVO();
                vo.setBorrowId(rs.getString("ID"));
                vo.setMyItem(rs.getString("MY_ITEM"));
                vo.setItemExplain(rs.getString("ITEM_EXPLAIN"));
                vo.setItemUrl(rs.getString("ITEM_URL"));
                vo.setItemQuantity(rs.getInt("ITEM_QUANTITY"));
                vo.setItemPrice(rs.getInt("ITEM_PRICE"));
                vo.setMyDate(rs.getDate("MY_DATE"));
                vo.setIsBorrowed(rs.getInt("IS_BORROWED"));
                return vo;
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    // 아이디 별로 리스트보이기
    public List<MyLendVO> getById(String borrowId) {
        List<MyLendVO> IdList = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String sql = "SELECT MY_NO, MY_ITEM, IS_BORROWED FROM 내빌드 WHERE ID=? ORDER BY MY_DATE DESC";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1,borrowId);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                int myNo = rs.getInt("MY_NO");
                String myItem = rs.getString("MY_ITEM");
                int isBorrowed = rs.getInt("IS_BORROWED");

                MyLendVO vo = new MyLendVO();
                vo.setMyNo(myNo);
                vo.setMyItem(myItem);
                vo.setIsBorrowed(isBorrowed);
                IdList.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IdList;
    }

    // 내빌드 글 수정
    public boolean myLendEdit (String myItem, String itemExplain, String itemUrl, int itemQuantity, int itemPrice, int isBorrowed, int myNo) {
        String sql = "UPDATE 내빋드 SET MY_ITEM=?, ITEM_EXPLAIN=?, ITEM_URL = ?, ITEM_QUANTITY=?, ITEM_PRICE=?, IS_BORROWED=? WHERE MY_NO = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, myItem);
            pStmt.setString(2, itemExplain);
            pStmt.setString(3, itemUrl);
            pStmt.setInt(4, itemQuantity);
            pStmt.setInt(5, itemPrice);
            pStmt.setInt(6, isBorrowed);
            pStmt.setInt(7, myNo);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Common.close(pStmt);
        Common.close(conn);
        return true;
    }

    // 내빌드 삭제
    public boolean myLendDelete(int myNo) {
        String sql = "DELETE FROM 내빌드 WHERE MY_NO =? ";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, myNo);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Common.close(pStmt);
        Common.close(conn);
        return true;
    }




}
