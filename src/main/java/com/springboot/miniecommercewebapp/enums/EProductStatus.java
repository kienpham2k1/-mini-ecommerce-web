package com.springboot.miniecommercewebapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EProductStatus {
    PRODUCT_STATUS_001(1,"STOCKING"),
    PRODUCT_STATUS_002(2,"OUT OF STOCK"),
    PRODUCT_STATUS_003(3,"DELETED"),
    ;
    private int id;
    private String productStausDescripstion;
}
