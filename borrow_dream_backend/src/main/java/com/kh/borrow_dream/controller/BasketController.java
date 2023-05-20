package com.kh.borrow_dream.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.kh.borrow_dream.dao.CartDAO;
import com.kh.borrow_dream.vo.CartVO;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BasketController {
    @GetMapping("/cart")
    public ResponseEntity<List<CartVO>> cartList(@RequestParam String id) {
    CartDAO dao = new CartDAO();
    List<CartVO> list = dao.cartList(id);
    return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity<Integer> listCnt (@RequestParam String id){
        CartDAO dao = new CartDAO();
        int cnt = dao.listCnt(id);
        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }

    @PostMapping("/cartItem/del")
    public ResponseEntity<Boolean> deleteCartItem(@RequestBody Map<String, String> delData) {
        String getUserId = delData.get("userId");
        String getPname = delData.get("pname");
        CartDAO dao = new CartDAO();
        boolean isTrue = dao.deleteCartItem(getUserId, getPname);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }
   
    @PostMapping("/cartItem/quan/update")
    public ResponseEntity<Boolean> updateQuantity(@RequestBody Map<String, String> upData) {
        String getUserId = upData.get("userId");
        String getPname = upData.get("pname");
        int getQuantity = Integer.parseInt(upData.get("quantity"));
        CartDAO dao = new CartDAO();
        boolean isTrue = dao.updateQuantity(getUserId, getPname, getQuantity);
        return new ResponseEntity<Boolean>(isTrue, HttpStatus.OK);
    }   

    @PostMapping("/cart/check")
    public ResponseEntity<List<CartVO>> checkCartList(@RequestBody Map<String, String> ckData) {
        String getUserId = ckData.get("userId");
        String getPname = ckData.get("pname");
        CartDAO dao = new CartDAO();
        List<CartVO> list = dao.checkList(getUserId, getPname);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
