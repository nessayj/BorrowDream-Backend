package com.kh.borrow_dream.dao;

import com.kh.borrow_dream.common.Common;
import com.kh.borrow_dream.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
//데이터베이스와의 상호작용을 위한 스프링의 컴포넌트입니다.
@Slf4j
//해당 클래스에서 자동으로 로깅을 위한 logger 객체를 생성합니다.



public class ProductDao {
    Connection conn = null;
    //  데이터베이스에 접속하고 쿼리를 실행합니다.
    Statement statement = null;
    //  SQL 쿼리를 실행하기 위한 객체입니다.
    ResultSet rs = null;
    //  SQL 쿼리를 실행한 결과를 담는 객체입니다.
    PreparedStatement pstmt = null;
//  미리 준비된 SQL 쿼리를 실행하기 위한 객체입니다
//   null 사용은 객체가 초기화되면 오류가 않아서 사용

    /**
     * 카테고리번호를 기준으로 조회하여 상품의 리스트 정보를 반환 .
     * //select * from 상품정보 where category_no = 1001(케리어);
     */
    public ResponseEntity<List<ProductVo>> getProducts(Integer categoryNo) {
        List<ProductVo> productVoList = new ArrayList<>();
        // 상품정보 조회 (상품명이 동일한 것)
        String sql = "SELECT * FROM 상품정보 WHERE CATEGORY_NO = " + categoryNo;
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            productVoList = productMapper(rs);
        } catch (SQLException sqlException) {
            log.error("SQL문에 문제가 있거나 데이터베이스에 문제가 발생하였습니다 !");
            System.out.println(sqlException.getMessage());
        } finally {
            // 반드시 데이터베이스와의 연결이 존재한다면 삭제처리 해줘야 합니다.
            if (conn != null) Common.close(conn);
            if (statement != null) Common.close(statement);
            if (rs != null) Common.close(rs);
        }
        if (productVoList.isEmpty()) {
            //조회한 상품 결과값이 존재 하지 않을경우 400 Status로 리턴한다.
            return ResponseEntity.badRequest().build();
        } else {
            //조회 한 상품 결과가 존재 할 경우 20
            // 0 응답과 함께 데이터를 반환한다.
            return ResponseEntity.ok(productVoList);
        }
    }

    /**
     * 상품번호를 기준으로 조회하여 1건 상품의 상세정보를 반환하는 기능을 하는 DAO 입니다.
     *
     * @param pNo*
     * @return ResponseEntity<Product>
     */
    public ResponseEntity<ProductVo> getProduct(String pName) {
        List<ProductVo> productVoList = new ArrayList<>();
        // 상품정보 조회 (상품명이 동일한 것)
        String sql = "SELECT * FROM 상품정보 WHERE P_NAME = " + pName;
        try {
            conn = Common.getConnection();
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            productVoList = productMapper(rs);
        } catch (SQLException sqlException) {
            // System.out.println은 서버 빌드시 자원을 많이 소모하기 때문에 @slf4j 어노테이션을 이용하여
            // 자원소모가 적은 log로 출력한다.
            log.error("SQL문에 문제가 있거나 데이터베이스에 문제가 발생하였습니다 !");
            log.error("에러 상세 : " + sqlException);
        } finally {
            // 반드시 데이터베이스와의 연결이 존재한다면 삭제처리 해줘야 합니다.
            if (conn != null) Common.close(conn);
            if (statement != null) Common.close(statement);
            if (rs != null) Common.close(rs);
        }
        if (productVoList.isEmpty()) {
            //조회한 상품 결과값이 존재 하지 않을경우 400 Status로 리턴한다.
            return ResponseEntity.badRequest().build();
        } else {
            //조회 한 상품 결과가 존재 할 경우 200 응답과 함께 데이터를 반환한다.
            return ResponseEntity.ok(productVoList.get(0));
        }
    }

    /**
     * 쿼리를 실행하여 도출된 결과 데이터인 ResultSet에서, ProductVO에 값을 담아주는 기능을 하는 메소드.
     *
     * @param rs
     * @return List<ProductVo>
     * @throws SQLException
     */


    private List<ProductVo> productMapper(ResultSet rs) throws SQLException {
        // 빈배열로 상품 초기화
        List<ProductVo> productVoList = new ArrayList<>();

        //조회 결과를 담고있는 ResultSet에서 결과를 추출해 매핑한다.
        //해당 리스트는 복수개의 데이터도 처리 할 수 있게 리스트에 담아서 처리하는 구조로 진행한다.
        while (rs.next()) {
            // VO는 불변 객체여야만 하며 속성값이 모두 같을 경우 같은객체라고 지칭 되어야 한다. 고로 불변성을 유지하기위해 객체 생성시 모든데이터를 인입시키고
            // SETTER를 따로 구현하지 않는다.
//            ProductVo productVo = new ProducVo();
//            productVo.setPNO(rs.getInt("P_NO"));
            //모든 전역변수를 할당해주는 생성자를 VO에 미리 선언하고, 해당데이터를 객체를 만듦과 동시에 데이터를 할당해준다.
            ProductVo productVo = new ProductVo
                    (rs.getInt("P_NO"),
                            rs.getString("P_NAME"),
                            rs.getInt("P_PRICE"),
                            rs.getString("P_img"),
                            rs.getString("P_DESCRIPTION"),
                            rs.getInt("P_QUANTITY"),
                            rs.getInt("CATEGORY_NO"));
            //리스트에 데이터 삽입.
            productVoList.add(productVo);
        }
        return productVoList;
    }


    public boolean registerProduct(ProductVo product) {
        int result = 0;
        String sql = "INSERT INTO 상품정보(CATEGORY_NO, P_NO, P_NAME, P_PRICE, P_img, P_DESCRIPTION, P_QUANTITY) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = Common.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, product.getCategoryNo());
            pstmt.setInt(2, product.getPNo());
            pstmt.setString(3, product.getPName());
            pstmt.setInt(4, product.getPPrice());
            pstmt.setString(5, product.getPImg());
            pstmt.setString(6, product.getPDescription());
            pstmt.setInt(7, product.getPQuantity());
            result = pstmt.executeUpdate();
            System.out.println("상품 등록 DB 결과 확인: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(pstmt);
            Common.close(conn);
        }
        return result == 1;
    }
}