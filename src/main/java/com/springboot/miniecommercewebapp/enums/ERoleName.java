package com.springboot.miniecommercewebapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERoleName {
    ROLE_ADMIN(1, "ROLE_ADMIN"), ROLE_USER(2, "ROLE_USER");
    private int id;
    private String name;

    public static String getRoleName(int id) {
        switch (id) {
            case 1:
                return ROLE_ADMIN.name;
            case 2:
                return ROLE_USER.name;
            default:
                return null;
        }
    }
}
