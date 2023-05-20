package com.kh.borrow_dream.vo;


import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductVo {
    private Integer pNo;
    private String pName;
    private int pPrice;
    private String pImg;
    private String pDescription;
    private int pQuantity;
    private Integer categoryNo;

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