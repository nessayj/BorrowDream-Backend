package com.kh.borrow_dream.controller;


import com.kh.borrow_dream.dao.ReviewDAO;
import com.kh.borrow_dream.vo.ReviewVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReviewController {
    private final ReviewDAO reviewDAO;
    public ReviewController(ReviewDAO reviewDAO) { this.reviewDAO = reviewDAO; }

    //후기 게시판리스트
    @GetMapping("/review-list")
    public ResponseEntity<List<ReviewVO>> getAllReview() {
        List<ReviewVO> reviewList = reviewDAO.getAllReview();
        return new ResponseEntity<>(reviewList, HttpStatus.OK);
    }

    // 후기 아이디 별 글 가지고오기
    @GetMapping("/reviewById")
    public ResponseEntity<List<ReviewVO>> getById(@RequestParam("rId") String rId) {
        List<ReviewVO> listById = reviewDAO.getById(rId);
        return new ResponseEntity<>(listById, HttpStatus.OK);
    }

    //후기작성
    @PostMapping("/review-write")
    public ResponseEntity<Boolean> writeReview(@RequestBody Map<String, String> data) {
        String getRTitle = (String)  data.get("rTitle");
        String getRId = (String) data.get("rId");
        String getRContents = (String) data.get("rContents");
        String getRUrl = (String) data.get("rUrl");
        int getYouLike = Integer.parseInt(data.get("youLike"));
        ReviewDAO dao = new ReviewDAO();
        boolean rst = dao.writeReview(getRTitle, getRId, getRContents, getRUrl, getYouLike);
        if(rst) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.OK);
    }

    // 후기 작성글 보기
    @GetMapping("/review-list/review")
    public  ResponseEntity<ReviewVO> viewReview(@RequestParam("reviewNo") int reviewNo) {
        ReviewVO review = reviewDAO.viewReview(reviewNo);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // 후기 수정
    @PostMapping("/review-edit")
    public ResponseEntity<Boolean> reviewEdit(@RequestBody Map<String, String> data) {
        String rTitle = data.get("rTitle");
        String rContents = data.get("rContents");
        String rUrl = data.get("rUrl");
        int youLike = Integer.parseInt(data.get("youLike"));
        int reviewNo = Integer.parseInt( data.get("reviewNo"));
        boolean rst = reviewDAO.reviewEdit(rTitle, rContents, rUrl, youLike, reviewNo);
        if(rst) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }

    // 후기 삭제
    @PostMapping("/review-delete")
    public ResponseEntity<Boolean> reviewDelete(@RequestBody Map<String, String> data) {
        int reviewNo = Integer.parseInt(data.get("reviewNo"));
        boolean rst = reviewDAO.reviewDelete(reviewNo);
        if(rst) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }


}
