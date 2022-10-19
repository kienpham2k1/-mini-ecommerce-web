package com.springboot.miniecommercewebapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EUserStatus {
    PRODUCT_STATUS_001(1,"USING"),
    PRODUCT_STATUS_002(2,"DELETED")
    ;
    private int id;
    private String userStausDescripstion;
}
