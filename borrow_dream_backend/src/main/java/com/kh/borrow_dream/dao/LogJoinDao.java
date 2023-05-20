package com.kh.borrow_dream.dao;

import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.LogJoinVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogJoinDao {
    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pStmt = null;

    // 회원가입 여부 확인
    public boolean customRegCheck(String id) {
        boolean isNotReg = false;
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 회원가입 WHERE ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);
            if (rs.next()) isNotReg = false;
            else isNotReg = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return isNotReg; // 가입 되어 있으면 false, 가입이 안되어 있으면 true
    }

    // 회원 가입
    public boolean customRegister(String name, String id, String pwd, String tel, String email, String addr) {
        System.out.println(name + id + pwd + tel + email + addr); // 출력 오류 시 출력 시도

//        email = "dusn0301@naver.com";
        int result = 0;
        String sql = "INSERT INTO 회원가입(NAME, ID, PWD, TEL, EMAIL, ADDR, JOIN) VALUES(?, ?, ?, ?, ?, ?, SYSDATE)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            pStmt.setString(2, id);
            pStmt.setString(3, pwd);
            pStmt.setString(4, tel);
            pStmt.setString(5, email);
            pStmt.setString(6, addr);
            result = pStmt.executeUpdate();
            System.out.println("회원 가입 DB 결과 확인 : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);

        if (result == 1) return true;
        else return false;
    }

    // 아이디 찾기
    public String findId(String name, String email) {
        String sqlId = null;

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql = "SELECT * FROM 회원가입 WHERE NAME = " + "'" + name + "'" + " AND EMAIL = " + "'" + email + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) { // 읽은 데이터가 있으면 true
                sqlId = rs.getString("ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        return sqlId;
    }

    // 비밀번호 찾기
    public String findPwd(String id, String email) {
        String sqlPwd = null;

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 회원가입 WHERE ID = " + "'" + id + "'" + " AND EMAIL = " + "'" + email + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                sqlPwd = rs.getString("PWD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
        return sqlPwd;
    }


    // 로그인 체크
    public boolean checkLogin(String id, String pwd) { // axios에서 전달하는 값(프엔에서 입력한 값)
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement(); // Statement 객체 얻기
            String sql = "SELECT * FROM 회원가입 WHERE ID = " + "'" + id + "'";
            rs = stmt.executeQuery(sql);

            while (rs.next()) { // 읽은 데이터가 있으면 true
                String sqlId = rs.getString("ID"); // 쿼리문 수행 결과에서 ID값을 가져 옴
                String sqlPwd = rs.getString("PWD");
                System.out.println("ID : " + sqlId);
                System.out.println("PWD : " + sqlPwd);
                if (id.equals(sqlId) && pwd.equals(sqlPwd)) {
                    Common.close(rs);
                    Common.close(stmt);
                    Common.close(conn);
                    return true;
                }
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 회원정보 전체 조회
    public List<LogJoinVo> customSelect() { // 엄격한 타입 체크
        List<LogJoinVo> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 회원가입";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("NAME");
                String id = rs.getString("ID");
                String pwd = rs.getString("PWD");
                String tel = rs.getString("TEL");
                String email = rs.getString("EMAIL");
                String addr = rs.getString("ADDR");
                Date join = rs.getDate("JOIN");

                LogJoinVo vo = new LogJoinVo();
                vo.setName(name);
                vo.setId(id);
                vo.setPwd(pwd);
                vo.setTel(tel);
                vo.setEmail(email);
                vo.setAddr(addr);
                vo.setJoin(join);
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

    // 마이페이지 정보 조회
    public LogJoinVo selectOne(String id) {
        LogJoinVo vo = null;

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM 회원가입 WHERE ID = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            rs = pStmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("NAME");
                String pwd = rs.getString("PWD");
                String tel = rs.getString("TEL");
                String email = rs.getString("EMAIL");
                String addr = rs.getString("ADDR");
                Date join = rs.getDate("JOIN");

                vo = new LogJoinVo();
                vo.setName(name);
                vo.setId(id);
                vo.setPwd(pwd);
                vo.setTel(tel);
                vo.setEmail(email);
                vo.setAddr(addr);
                vo.setJoin(join);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }


    // 회원정보 수정 업데이트
    public boolean mypageUpdate(String pwd, String tel, String addr, String id) {
    System.out.println(pwd+tel+addr);
        try {
            conn = Common.getConnection();
            String sql = "UPDATE 회원가입 SET PWD = ?, TEL = ?, ADDR = ? WHERE ID = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, pwd);
            pStmt.setString(2, tel);
            pStmt.setString(3, addr);
            pStmt.setString(4, id); // 수정 대상 아이디
            pStmt.executeUpdate();


            Common.close(pStmt);
            Common.close(conn);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    /* 회원탈퇴 */

    public boolean customDelete (String id){
        int result = 0;
        String sql = "DELETE FROM 회원가입 WHERE ID = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, id);
            result = pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        if (result == 1) return true;
        else return false;
    }

}
