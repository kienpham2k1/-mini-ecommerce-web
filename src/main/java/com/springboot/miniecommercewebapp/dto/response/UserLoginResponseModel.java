package com.springboot.miniecommercewebapp.dto.response;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Getter
@RequiredArgsConstructor
public class UserLoginResponseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
}