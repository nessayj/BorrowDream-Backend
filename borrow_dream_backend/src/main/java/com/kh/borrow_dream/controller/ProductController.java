package com.kh.borrow_dream.controller;


import com.kh.borrow_dream.dao.ProductDao;
import com.kh.borrow_dream.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//HTTP 응답 본문으로 변환하여 반환합니다.
@RequestMapping("/api/product")
//클라이언트의 HTTP 요청을 어떤 메서드가 처리할지를 매핑
@CrossOrigin(origins = "http://localhost:3000")
//서로 다른 도메인 간의 자원 공유를 가능하게 합니다.
@RequiredArgsConstructor
//Lombok 어노테이션 중 하나입니다. 이 어노테이션은 클래스의 생성자를 자동으로 생성합니다 파이널로 선언 해 줌?
public class ProductController {
    private final ProductDao productDao;

    // 상품 정보들을 반환하는 API
    @GetMapping("/category/{categoryNo}")
    //경로의 GET 요청에 대한 응답을 처리
    public ResponseEntity<List<ProductVo>> getProducts(@PathVariable("categoryNo") Integer categoryNo) {
        //@PathVariable("categoryNo")는 URL의 {categoryNo} 파라미터 값을 메서드 인자로 받아서 처리하겠다는 것을 나타냅니다.
        //categoryNo는 Integer 형식으로 지정되어 있으며, 이를 바탕으로 DAO에서 해당 카테고리에 속한 상품 정보를 조회합니다.
        System.out.println("상품번호 :" + categoryNo);
        return productDao.getProducts(categoryNo);

    }

    //RestFul한 API 개발원칙상, id와 같은 고유값은 PathVariable로 명시적으로 접근하는게 좋다.
    @GetMapping("/{pName}")
    public ResponseEntity<ProductVo> getProduct(@PathVariable("pName") String pName) {
        return productDao.getProduct(pName);
    }


    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadProduct(@RequestBody Map<String, String> regData) {
        String getCategoryNo = regData.get("CATEGORY_NO");
        int categoryNo = Integer.parseInt(getCategoryNo);

        String getpNo = regData.get("P_NO");
        int pNo = Integer.parseInt(getpNo);

        String getpName = regData.get("P_NAME");

        String getpPrice = regData.get("P_PRICE");
        int pPrice = Integer.parseInt(getpPrice);

        String getpImg = regData.get("P_img");
        String getpDescription = regData.get("P_DESCRIPTION");

        String getpQuantity = regData.get("P_QUANTITY");
        int pQuantity = Integer.parseInt(getpQuantity);

        ProductVo product = new ProductVo(pNo, getpName, pPrice, getpImg, getpDescription, pQuantity, categoryNo);
        boolean isTrue = productDao.registerProduct(product);
        return new ResponseEntity<>(isTrue, HttpStatus.OK);
    }

    @GetMapping("/productOrder")
    public ResponseEntity<ProductVo> directOrder(@RequestParam String pName) {
        System.out.println("id : " + pName);
        ProductDao dao = new ProductDao();
        ProductVo vo = dao.productList(pName);
        return new ResponseEntity<>(vo, HttpStatus.OK);
    }
}