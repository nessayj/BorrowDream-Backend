package com.kh.borrow_dream.dao;

import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.ReviewVO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class ReviewDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 후기목록
    public List<ReviewVO> getAllReview() {
        List<ReviewVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT REVIEW_NO, R_TITLE, R_CONTENTS, R_ID, URL, YOU_LIKE, R_DATE FROM REVIEW ORDER BY R_DATE DESC";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int reviewNo = rs.getInt("REVIEW_NO");
                String rTitle = rs.getString("R_TITLE");
                String rContents = rs.getString("R_CONTENTS");
                String rId = rs.getString("R_ID");
                String rUrl = rs.getString("URL");
                int youLike = rs.getInt("YOU_LIKE");
                Date rDate = rs.getDate("R_DATE");

                ReviewVO vo = new ReviewVO();
                vo.setReviewNo(reviewNo);
                vo.setRTitle(rTitle);
                vo.setRID(rId);
                vo.setRContents(rContents);
                vo.setRUrl(rUrl);
                vo.setYouLike(youLike);
                vo.setRDate(rDate);
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

    // 후기작성
    public boolean writeReview(String rTitle, String rId, String rContents, String rUrl, int youLike) {
        int rs = 0;
        String sql = "INSERT INTO REVIEW VALUES (seq_REVIEW.NEXTVAL, ?, ?, ?, ?, ? ,SYSDATE)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, rTitle);
            pStmt.setString(2,rId);
            pStmt.setString(3, rContents);
            pStmt.setString(4, rUrl);
            pStmt.setInt(5, youLike);
            rs = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(rs == 1) return true;
        else return false;
    }

    // 리뷰 글 보기
    public ReviewVO viewReview(int reviewNo) {
        System.out.println("리뷰넘버 들어오나요" + reviewNo);
        ReviewVO vo = null;
        try {
            conn = Common.getConnection();
            String sql = "SELECT REVIEW_NO, R_TITLE, R_ID, R_CONTENTS, R_DATE, YOU_LIKE, URL FROM REVIEW WHERE REVIEW_NO =?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, reviewNo);
            rs = pStmt.executeQuery();

            if(rs.next()) {
                vo = new ReviewVO();
                vo.setReviewNo(rs.getInt("REVIEW_NO"));
                vo.setRTitle(rs.getString("R_TITLE"));
                vo.setRID(rs.getString("R_ID"));
                vo.setRContents(rs.getString("R_CONTENTS"));
                vo.setRDate(rs.getDate("R_DATE"));
                vo.setYouLike(rs.getInt("YOU_LIKE"));
                vo.setRUrl(rs.getString("URL"));
                return vo;
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    // 후기 글 수정
    public boolean reviewEdit( String rTitle, String rContents, String rUrl,int youLike, int reviewNo) {
        System.out.println(rTitle + " " +  rContents + " " + rUrl + " " +  youLike + " " + reviewNo);
        String sql = "UPDATE REVIEW SET R_TITLE=?, R_CONTENTS=?, URL =?, YOU_LIKE =?, R_DATE=SYSDATE WHERE REVIEW_NO=?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, rTitle);
            pStmt.setString(2, rContents);
            pStmt.setString(3, rUrl);
            pStmt.setInt(4, youLike);
            pStmt.setInt(5, reviewNo);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Common.close(pStmt);
        Common.close(conn);
        return true;
    }

    // 후기 삭제

    public boolean reviewDelete(int reviewNo) {
        String sql = "DELETE FROM REVIEW WHERE REVIEW_NO =?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, reviewNo);
            pStmt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Common.close(pStmt);
        Common.close(conn);
        return true;
    }

}
