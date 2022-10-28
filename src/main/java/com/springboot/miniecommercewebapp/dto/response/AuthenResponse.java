package com.springboot.miniecommercewebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenResponse {
    private int status;
    private String error;
    private String message;
    private String path;
}
