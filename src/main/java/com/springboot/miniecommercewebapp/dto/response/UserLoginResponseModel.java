package com.springboot.miniecommercewebapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserLoginResponseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String Role;
    private final String token;
}