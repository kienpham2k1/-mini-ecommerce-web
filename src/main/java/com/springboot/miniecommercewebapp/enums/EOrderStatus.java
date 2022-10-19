package com.springboot.miniecommercewebapp.enums;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EOrderStatus {
    ORDER_STATUS_001(1, "TO PAY"),
    ORDER_STATUS_002(2, "TO SHIP"),
    ORDER_STATUS_003(3, "TO RECEIVE"),
    ORDER_STATUS_004(4, "COMPLETED"),
    ORDER_STATUS_005(5, "CANCELLED")
    ;
    private int id;
    private String orderStatusDescripstion;
}
