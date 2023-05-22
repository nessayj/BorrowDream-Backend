package com.kh.borrow_dream.controller;


import com.kh.borrow_dream.dao.MyLendDAO;
import com.kh.borrow_dream.vo.MyLendVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MyLendController {
    private final MyLendDAO myLendDAO;

    public MyLendController(MyLendDAO myLendDAO) {this.myLendDAO = myLendDAO;}

    // 목록가지고오기
    @GetMapping("/myLend")
    public ResponseEntity<List<MyLendVO>> getAllMyLend() {
        List<MyLendVO> myLend = myLendDAO.getAllMyLend();
        return new ResponseEntity<>(myLend, HttpStatus.OK);
    }

    // 내빌드작성
    @PostMapping("/myLentItem/write")
    public ResponseEntity<Boolean> writeMyLend(@RequestBody Map<String, Object> data) {
        String getBorrowId = (String) data.get("borrowId");
        String getMyItem = (String) data.get("myItem");
        String getItemExplain = (String) data.get("itemExplain");
        String getItemUrl = (String) data.get("itemUrl");
        int getItemQuantity = Integer.parseInt((String) data.get("itemQuantity"));
        int getItemPrice = Integer.parseInt((String) data.get("itemPrice"));
        MyLendDAO dao = new MyLendDAO();
        boolean rst = dao.writeMyLend(getBorrowId, getMyItem, getItemExplain,getItemUrl,getItemQuantity,getItemPrice);
        if(rst) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.OK);
    }

    // 글 상세보기
    @GetMapping("/viewMyItem")
    public ResponseEntity<MyLendVO> viewMyLend(@RequestParam("myNo") int myNo) {
        MyLendVO myLend = myLendDAO.viewMyLend(myNo);
        return new ResponseEntity<>(myLend, HttpStatus.OK);
    }

    // 글 수정
    @PostMapping("/myLend-edit")
    public ResponseEntity<Boolean> myLendEdit(@RequestBody Map<String, Object> data) {
        String myItem = (String) data.get("myItem");
        String itemExplain = (String) data.get("itemExplain");
        String itemUrl = (String) data.get("itemUrl");
        int itemQuantity = Integer.parseInt((String) data.get("itemQuantity"));
        int itemPrice = Integer.parseInt((String) data.get("itemPrice"));
        int isBorrowed = 0;
        if(data.get("isBorrowed") instanceof Boolean && (boolean) data.get("isBorrowed")){
            isBorrowed = 1;
        }
        int myNo = Integer.parseInt((String) data.get("myNo"));
        boolean result = myLendDAO.myLendEdit(myItem, itemExplain, itemUrl, itemQuantity, itemPrice, isBorrowed, myNo);
        if(result) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }
    // 삭제
    @PostMapping("/myLend-delete")
    public ResponseEntity<Boolean> myLendData(@RequestBody Map<String, String> data) {
        int myNo = Integer.parseInt(data.get("myNo"));
        boolean result = myLendDAO.myLendDelete(myNo);
        if(result) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(null, HttpStatus.OK);
    }

    // 아이디 별로 글리스트 가지고오기
    @GetMapping("/myLendById")
    public ResponseEntity<List<MyLendVO>> getById(@RequestParam("borrowId") String borrowId) {
        List<MyLendVO> idList = myLendDAO.getById(borrowId);
        return new ResponseEntity<>(idList, HttpStatus.OK);
    }

}
