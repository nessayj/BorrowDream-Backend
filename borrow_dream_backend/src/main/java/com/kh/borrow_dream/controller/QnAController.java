package com.kh.borrow_dream.controller;


import com.kh.borrow_dream.dao.QnADAO;
import com.kh.borrow_dream.vo.QnAVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class QnAController {
    private final QnADAO qnaDAO;

    public QnAController(QnADAO qnaDAO) { this.qnaDAO = qnaDAO;}

    @GetMapping("/qna-list")
    public ResponseEntity<List<QnAVO>> getAllQna() {
        List<QnAVO> qna = qnaDAO.getAllQna();
        return new ResponseEntity<>(qna, HttpStatus.OK);
    }
}
