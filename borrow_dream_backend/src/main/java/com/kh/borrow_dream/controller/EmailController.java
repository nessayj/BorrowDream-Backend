package com.kh.borrow_dream.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kh.borrow_dream.EmailService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class EmailController {
    @Autowired
    private EmailService es = new EmailService();

    @PostMapping("/signup/email")
    public ResponseEntity<String> mailConfirm(@RequestBody Map<String, String> email) throws Exception {
        System.out.println("회원가입 이메일 인증코드 전송");
        String mail = email.get("email");
        String authCode = es.authCodeSendMessage(mail);
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }
    
    
}
