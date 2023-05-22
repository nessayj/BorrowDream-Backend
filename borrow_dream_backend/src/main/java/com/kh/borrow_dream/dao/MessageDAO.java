package com.kh.borrow_dream.dao;

import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.MessageVO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Repository
public class MessageDAO {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    //메세지목록
    public List<MessageVO> getAllMessage() {
        List<MessageVO> list = new ArrayList<>();

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 메세지 ORDER BY MSG_DATE DESC";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int msgNo = rs.getInt("MSG_NO");
                String sender = rs.getString("SENDER");
                String receiver = rs.getString("RECEIVER");
                String msgTitle = rs.getString("MSG_TITLE");
                String msgContents = rs.getString("MSG_CONTENTS");
                Date msgDate = rs.getDate("MSG_DATE");

                MessageVO vo = new MessageVO();
                vo.setMsgNo(msgNo);
                vo.setSender(sender);
                vo.setReceiver(receiver);
                vo.setMsgTitle(msgTitle);
                vo.setMsgContents(msgContents);
                vo.setMsgDate(msgDate);
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

    // 받는사람 메세지 상세보기
    public MessageVO receiverMsg(int msgNo) {
        MessageVO vo = null;
        try {
            conn = Common.getConnection();
            String sql = "SELECT SENDER, MSG_TITLE, MSG_CONTENTS, MSG_DATE FROM 메세지 WHERE MSG_NO=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, msgNo);
            rs = pStmt.executeQuery();

            if(rs.next()) {
                vo = new MessageVO();
                vo.setSender(rs.getString("SENDER"));
                vo.setMsgTitle(rs.getString("MSG_TITLE"));
                vo.setMsgContents(rs.getString("MSG_CONTENTS"));
                vo.setMsgDate(rs.getDate("MSG_DATE"));
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


    // 받는사람 메세지 리스트보기
    public List<MessageVO> receiverMsgList(String receiver) {
        List<MessageVO> msg = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String sql = "SELECT MSG_NO, SENDER, MSG_TITLE, MSG_CONTENTS, MSG_DATE FROM 메세지 WHERE RECEIVER=? ORDER BY MSG_DATE";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, receiver);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                int msgNo = rs.getInt("MSG_NO");
                String sender = rs.getString("SENDER");
                String msgTitle = rs.getString("MSG_TITLE");
                String msgContents = rs.getString("MSG_CONTENTS");
                Date msgDate = rs.getDate("MSG_DATE");

                MessageVO vo = new MessageVO();
                vo = new MessageVO();
                vo.setMsgNo(msgNo);
                vo.setSender(sender);
                vo.setMsgTitle(msgTitle);
                vo.setMsgContents(msgContents);
                vo.setMsgDate(msgDate);
                msg.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }
    // 보낸 메세지보기
    public MessageVO senderMsg(int msgNo) {
        MessageVO vo = null;
        try {
            conn = Common.getConnection();
            String sql = "SELECT RECEIVER, MSG_TITLE, MSG_CONTENTS, MSG_DATE FROM 메세지 WHERE MSG_NO=?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, msgNo);
            rs = pStmt.executeQuery();

            if(rs.next()) {
                vo = new MessageVO();
                vo.setReceiver(rs.getString("RECEIVER"));
                vo.setMsgTitle(rs.getString("MSG_TITLE"));
                vo.setMsgContents(rs.getString("MSG_CONTENTS"));
                vo.setMsgDate(rs.getDate("MSG_DATE"));
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

    // 보낸 메세지보기 리스트
    public List<MessageVO> senderMsgList(String sender) {
        List<MessageVO> msg = new ArrayList<>();
        try {
            conn = Common.getConnection();
            String sql = "SELECT MSG_NO, RECEIVER, MSG_TITLE, MSG_CONTENTS, MSG_DATE FROM 메세지 WHERE SENDER=? ORDER BY MSG_DATE";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, sender);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                int msgNo = rs.getInt("MSG_NO");
                String receiver = rs.getString("RECEIVER");
                String msgTitle = rs.getString("MSG_TITLE");
                String msgContents = rs.getString("MSG_CONTENTS");
                Date msgDate = rs.getDate("MSG_DATE");

                MessageVO vo = new MessageVO();
                vo = new MessageVO();
                vo.setMsgNo(msgNo);
                vo.setReceiver(receiver);
                vo.setMsgTitle(msgTitle);
                vo.setMsgContents(msgContents);
                vo.setMsgDate(msgDate);
                msg.add(vo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    // 메세지 보내기
    public boolean sendMsg(String sender, String receiver, String msgTitle, String msgContents) {
        int rs = 0;
        String sql = "INSERT INTO 메세지 VALUES (seq_MSG.NEXTVAL, ?, ?, ?, ?, SYSDATE)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, sender);
            pStmt.setString(2, receiver);
            pStmt.setString(3, msgTitle);
            pStmt.setString(4, msgContents);
            rs = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if(rs == 1) return true;
        else return false;
    }



}
