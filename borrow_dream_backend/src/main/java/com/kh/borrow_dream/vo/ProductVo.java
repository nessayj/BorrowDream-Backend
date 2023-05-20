package com.kh.borrow_dream.vo;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVo {
    private Integer pNo; // 고유번호 (Primary Key)
    private String pName; //상품명
    private int pPrice; //상품 가격
    private String pImg; //상품이미지
    private String pDescription; //상품 설명
    private int pQuantity; //상품퀄리티
    private Integer categoryNo; //상품 카테고리 번호

    public ProductVo(Integer pNo, String pName, int pPrice, String pImg, String pDescription, int pQuantity, Integer categoryNo) {
        this.pNo = pNo;
        this.pName = pName;
        this.pPrice = pPrice;
        this.pImg = pImg;
        this.pDescription = pDescription;
        this.pQuantity = pQuantity;
        this.categoryNo = categoryNo;
    }
}
