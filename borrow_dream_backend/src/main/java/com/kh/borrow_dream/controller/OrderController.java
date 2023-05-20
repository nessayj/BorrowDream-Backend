package com.kh.borrow_dream.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kh.borrow_dream.dao.OrderDAO;
import com.kh.borrow_dream.vo.OrderVO;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class OrderController {
    @GetMapping("/order")
    public ResponseEntity<List<OrderVO>> orderList(@RequestParam String id){
        OrderDAO dao = new OrderDAO();
        List<OrderVO> list = dao.orderList(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/order/insert")
    public ResponseEntity<Boolean> orderInsert(@RequestBody Map<String, String> upData) {
        String getUserId = upData.get("userId");
        OrderDAO dao = new OrderDAO();
        boolean isTrue = dao.orderUpdate(getUserId);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
}
