package com.kh.borrow_dream.controller;


import com.kh.borrow_dream.dao.BoardDAO;
import com.kh.borrow_dream.vo.BoardVO;
import com.kh.borrow_dream.vo.CommentVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BoardController {
    private final BoardDAO boardDAO;
    public BoardController(BoardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }
    // GET : 목록조회; vscode에서 변수처럼 설저앻 놓은 걸 그대로 가지고와서 변수받음
    @GetMapping("/board-list")
    public ResponseEntity<List<BoardVO>> getAllBoards() {
        List<BoardVO> boards = boardDAO.getAllBoards();
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }

    // 문의글 작성
    @PostMapping("/board-list/write")
    public ResponseEntity<Boolean> writeInquiry(@RequestBody Map<String, Object> regData) {
        String getCategory = (String) regData.get("category");
        String getTitle = (String) regData.get("title");
        String getWriterId = (String) regData.get("writerId");
        String getContents = (String) regData.get("contents");
        int getBoardPwd = Integer.parseInt((String) regData.get("boardPwd"));
        int getIsUnknown = 1;
        if(regData.get("isUnknown") instanceof Boolean && (boolean) regData.get("isUnknown")) {
            getIsUnknown = 2;
        }
        BoardDAO dao = new BoardDAO();
        boolean rst = dao.writeInquiry(getCategory, getTitle, getWriterId, getContents, getBoardPwd, getIsUnknown);
        if(rst) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.OK);
    }
    // 문의글 열기 전 비밀번호 체크
    @PostMapping("/check-password")
    public ResponseEntity<Boolean> checkPassword(@RequestBody Map<String, String> requestBody) {
        int boardNo = Integer.parseInt(requestBody.get("boardNo"));
        int boardPwd = Integer.parseInt(requestBody.get("boardPwd"));
        boolean isCorrectPassword = boardDAO.checkPassword(boardNo, boardPwd);
        if (isCorrectPassword) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }

    }


    // 문의글 상세보기
    @GetMapping("/board-list/inquiry-view")
    public ResponseEntity<BoardVO> inquiryViewLoad(@RequestParam("boardNo") int boardNo) {
       BoardVO board = boardDAO.inquiryViewLoad(boardNo);
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    // 클릭 시 조회수 올리기
    @PostMapping("/views-up")
    public ResponseEntity<Integer> viewsUp(@RequestBody Map<String, String> data) {
        int boardNo = Integer.parseInt((String) data.get("boardNo"));
        boolean viewsUpdated = boardDAO.viewsUp(boardNo);
        return new ResponseEntity(viewsUpdated, HttpStatus.OK);
    }

    // 문의글 수정하기
    @PostMapping("/board-list/inquiry-edit")
    public ResponseEntity<Boolean> inquiryEdit(@RequestBody Map<String, Object> regData) {
        int boardNo = Integer.parseInt((String) regData.get("boardNo"));
        String writerId = (String) regData.get("writerId");
        String category = (String) regData.get("category");
        String title = (String) regData.get("title");
        String contents = (String) regData.get("contents");
        int isUnknown = 1;
        if(regData.get("isUnknown") instanceof Boolean && (boolean) regData.get("isUnknown")) {
            isUnknown = 2;
        }
        boolean result = boardDAO.inquiryEdit(boardNo, writerId, category, title, contents, isUnknown);
        if(result) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }



    // 문의글 삭제
    @PostMapping("/board-list/inquiry-delete")
    public ResponseEntity<Boolean> inquiryDelete(@RequestBody Map<String, String> data) {
        int boardNo = Integer.parseInt(data.get("boardNo"));
        boolean result = boardDAO.inquiryDelete(boardNo);
        if(result) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(null, HttpStatus.OK);
    }

    // 게시물에 댓글 작성하기
    @PostMapping("/comment-write")
    public ResponseEntity<Boolean> commentWrite(@RequestBody Map<String, String> data) {
        int boardNo = Integer.parseInt(data.get("boardNo"));
        String commentId = data.get("commentId");
        String contents = data.get("contents");
        boolean commentWrite = boardDAO.commentWrite(boardNo, commentId, contents);
        if (commentWrite) return new ResponseEntity(true, HttpStatus.OK);
        else return new ResponseEntity(false, HttpStatus.OK);
    }

    // boardNo과 일치하는 댓글(해당 글의 댓글)을 불러오기
    @GetMapping("/comment-view")
    public ResponseEntity<List<CommentVO>> commentView(@RequestParam int boardNo) {
        System.out.println(boardNo); // 보드넘버 넘어오는지 체크
        List<CommentVO> comments = boardDAO.commentListView(boardNo);
         return new ResponseEntity(comments, HttpStatus.OK);
    }

    // 댓글 삭제하기
    @PostMapping("/comment-delete")
    public ResponseEntity<Boolean> commentDelete(@RequestBody Map<String, Integer> data) {
        int commentNo = data.get("commentNo");
        boolean commentDelete = boardDAO.commentDelete(commentNo);
        if(commentDelete) return new ResponseEntity(commentDelete, HttpStatus.OK);
        else return new ResponseEntity(commentDelete, HttpStatus.OK);
    }

    // 댓글 수정하기
    @PostMapping("/comment-edit")
    public ResponseEntity<Boolean> commentEdit(@RequestBody Map<String, String> data) {
        String contents = data.get("contents");
        int commentNo = Integer.parseInt(data.get("commentNo"));
        boolean result = boardDAO.commentEdit(commentNo, contents);
        if(result) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.OK);
    }




}
