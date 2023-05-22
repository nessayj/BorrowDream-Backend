package com.kh.borrow_dream.dao;


import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.BoardVO;
import com.kh.borrow_dream.vo.CommentVO;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BoardDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 문의하기 리스트 조회
    public List<BoardVO> getAllBoards() {
        List<BoardVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql ="SELECT BOARD_NO, CATEGORY, TITLE, WRITER_ID, VIEWS, WRITE_DATE, ISUNKNOWN FROM 문의하기 ORDER BY WRITE_DATE DESC"; // 목록전체조회
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int boardNo = rs.getInt("BOARD_NO");
                String category = rs.getString("CATEGORY");
                String title = rs.getString("TITLE");
                String writerId = rs.getString("WRITER_ID");
                int views = rs.getInt("VIEWS");
                Date writeDate = rs.getDate("WRITE_DATE");
                int isUnknown = rs.getInt("ISUNKNOWN");


                BoardVO vo = new BoardVO();
                vo.setBoardNo(boardNo);
                vo.setCategory(category);
                vo.setTitle(title);
                vo.setWriterId(writerId);
                vo.setViews(views);
                vo.setWriteDate(writeDate);
                vo.setIsUnknown(isUnknown);
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

    // 아이디 별 문의하기 조회
    public List<BoardVO> getById(String writerId) {
        List<BoardVO> IdList = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String sql = "SELECT BOARD_NO, CATEGORY, TITLE, VIEWS, WRITE_DATE, BOARD_PWD, ISUNKNOWN FROM 문의하기 WHERE WRITER_ID=? ORDER BY WRITE_DATE DESC";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, writerId);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                int boardNo = rs.getInt("BOARD_NO");
                String category = rs.getString("CATEGORY");
                String title = rs.getString("TITLE");
                int views = rs.getInt("VIEWS");
                Date writeDate = rs.getDate("WRITE_DATE");
                int boardPwd = rs.getInt("BOARD_PWD");
                int isUnknown = rs.getInt("ISUNKNOWN");

                BoardVO vo = new BoardVO();
                vo.setBoardNo(boardNo);
                vo.setCategory(category);
                vo.setTitle(title);
                vo.setViews(views);
                vo.setWriteDate(writeDate);
                vo.setBoardPwd(boardPwd);
                vo.setIsUnknown(isUnknown);
                IdList.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IdList;
    }

    // 문의하기 작성
    public boolean writeInquiry(String category, String title, String writerId, String contents, int boardPwd, int isUnknown) {
        System.out.println("글쓴이 아이디 : " + writerId);
        int rs = 0;
        String sql = "INSERT INTO 문의하기 VALUES (seq_INQUIRY.NEXTVAL, ?, ?, ?, 0, SYSDATE, ?, ?, ?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, category);
            pStmt.setString(2, title);
            pStmt.setString(3, writerId);
            pStmt.setString(4, contents);
            pStmt.setInt(5, boardPwd);
            pStmt.setInt(6, isUnknown);
            rs = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(rs == 1) return true;
        else return false;

    }

    // 문의하기 글 보기 전 비밀번호 체크
    public boolean checkPassword(int boardNo, int boardPwd) {

        System.out.println("보드넘버: " + boardNo);
        System.out.println("비밀번호: " + boardPwd);
        try {
            conn = Common.getConnection();
            String sql = "SELECT COUNT(*) FROM 문의하기 WHERE BOARD_NO=? AND BOARD_PWD=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, boardNo);
            pStmt.setInt(2, boardPwd);
            rs = pStmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 1;  // 게시물 번호와 비밀번호가 일치하는 경우 true 반환
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        }
        return false;  // 예외 발생 시 false 반환
    }




    // 문의하기 글 보기
    public BoardVO inquiryViewLoad(int boardNo) {
        BoardVO vo = null;
        try {
            conn = Common.getConnection();
            String sql = "SELECT BOARD_NO, CATEGORY, TITLE, WRITER_ID, VIEWS, WRITE_DATE, CONTENTS FROM 문의하기 WHERE BOARD_NO=?";
            // boardNo로 받아야하기때문에 매개변수 추가하고 해당 매개변수를 사용하도록 수정해야함
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, boardNo);
            rs = pStmt.executeQuery();

            if (rs.next()) {
                vo = new BoardVO();
                vo.setBoardNo(rs.getInt("BOARD_NO"));
                vo.setCategory(rs.getString("CATEGORY"));
                vo.setTitle(rs.getString("TITLE"));
                vo.setWriterId(rs.getString("WRITER_ID"));
                vo.setViews(rs.getInt("VIEWS"));
                vo.setWriteDate(rs.getDate("WRITE_DATE"));
                vo.setContents(rs.getString("CONTENTS"));
                return vo;
            }
                Common.close(rs);
                Common.close(pStmt);
                Common.close(conn);
            } catch(Exception e){
                e.printStackTrace();
            }
            return vo;
        }

        // 조회수 올리기
        public boolean viewsUp(int boardNo) {
            String sql = "UPDATE 문의하기 SET VIEWS = VIEWS + 1 WHERE BOARD_NO=?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, boardNo);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            Common.close(pStmt);
            Common.close(conn);
            return true;
        }

        // 문의하기 글 수정하기
        public boolean inquiryEdit(int boardNo, String writerId, String category, String title, String contents, int isUnknown) {
            String sql = "UPDATE 문의하기 SET CATEGORY=?, TITLE=?, WRITE_DATE=SYSDATE, CONTENTS=?, ISUNKNOWN =? WHERE BOARD_NO=? AND WRITER_ID = ?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, category);
                pStmt.setString(2, title);
                pStmt.setString(3, contents);
                pStmt.setInt(4, isUnknown);
                pStmt.setInt(5,boardNo);
                pStmt.setString(6, writerId);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            Common.close(pStmt);
            Common.close(conn);
            return true;
        }


        // 문의글 삭제
        public boolean inquiryDelete(int boardNo) {
            String sql = "DELETE FROM 문의하기 WHERE BOARD_NO = ?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, boardNo);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            Common.close(pStmt);
            Common.close(conn);
            return true;
        }


        // 댓글 작성
        public boolean commentWrite(int boardNo, String commentId, String contents) {
            String sql = "INSERT INTO 댓글 VALUES(seq_COMMENT.NEXTVAL, ?, ?, ?, SYSDATE)";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, boardNo);
                pStmt.setString(2, commentId);
                pStmt.setString(3, contents);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            Common.close(pStmt);
            Common.close(conn);
            return true;
        }

        // 댓글 조회
        public List<CommentVO> commentListView (int boardNo) {
            List<CommentVO> list = new ArrayList<>();
            try {
                conn = Common.getConnection();
                String sql = "SELECT C.* FROM 문의하기 I JOIN 댓글 C ON I.BOARD_NO = C.BOARD_NO WHERE I.BOARD_NO=? ORDER BY COMMENT_TIME DESC";
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, boardNo);
                rs = pStmt.executeQuery();

                while (rs.next()) {
                    int commentNo = rs.getInt("COMMENT_NO");
                    int getBoardNo = rs.getInt("BOARD_NO");
                    String commentId = rs.getString("COMMENT_ID");
                    String contents = rs.getString("CONTENTS");
                    Date commentTime = rs.getDate("COMMENT_TIME");

                    CommentVO vo = new CommentVO();
                    vo.setCommentNo(commentNo);
                    vo.setBoardNo(getBoardNo);
                    vo.setCommentId(commentId);
                    vo.setContents(contents);
                    vo.setCommentTime(commentTime);
                    list.add(vo);
                }
                Common.close(rs);
                Common.close(pStmt);
                Common.close(conn);
            } catch(Exception e){
                e.printStackTrace();
            }
            return list;
        }

        // 댓글 수정
        public boolean commentEdit(int commentNo, String contents) {
            String sql = "UPDATE 댓글 SET CONTENTS = ?, COMMENT_TIME = SYSDATE WHERE COMMENT_NO = ?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, contents);
                pStmt.setInt(2, commentNo);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            Common.close(pStmt);
            Common.close(conn);
            return true;
        }

        // 댓글 삭제
        public boolean commentDelete(int commentNo) {
            String sql = "DELETE FROM 댓글 WHERE COMMENT_NO=?";
            try{
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1,commentNo);
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

