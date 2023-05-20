package com.kh.borrow_dream.controller;


import com.kh.borrow_dream.dao.MessageDAO;
import com.kh.borrow_dream.vo.MessageVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MessageController {
    private final MessageDAO messageDAO;
    public MessageController(MessageDAO messageDAO) {this.messageDAO = messageDAO;}

    @GetMapping("/msgList")
    public ResponseEntity<List<MessageVO>> getAllMessage() {
        List<MessageVO> msgList = messageDAO.getAllMessage();
        return new ResponseEntity<>(msgList, HttpStatus.OK);
    }

    // 작성자가 보낸 메세지 보기
    @GetMapping("/sender")
    public ResponseEntity<MessageVO> senderMsg(@RequestParam("sender") String sender) {
        MessageVO msgSender = messageDAO.senderMsg(sender);
        return new ResponseEntity<>(msgSender, HttpStatus.OK);
    }

    // 받은메세지보기
    @GetMapping("/receiver")
    public ResponseEntity<MessageVO> receiverMsg(@RequestParam("receiver") String receiver) {
        MessageVO msgReceiver = messageDAO.receiverMsg(receiver);
        return new ResponseEntity<>(msgReceiver, HttpStatus.OK);
    }

    // 메세지보내기
    @PostMapping("/writeMsg")
    public ResponseEntity<Boolean> sendMsg(@RequestBody Map<String, String> data) {
        String getSender = data.get("sender");
        String getReceiver = data.get("receiver");
        String getMsgTitle = data.get("msgTitle");
        String getMsgContents = data.get("msgContents");
        MessageDAO dao = new MessageDAO();
        boolean rst = dao.sendMsg(getSender, getReceiver, getMsgTitle, getMsgContents);
        if(rst) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else return new ResponseEntity<>(false, HttpStatus.OK);

    }

}
