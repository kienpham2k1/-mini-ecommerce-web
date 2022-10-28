package com.springboot.miniecommercewebapp.dto.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    int code;
    String message;
    @JsonInclude(value = Include.NON_NULL)
    Map<String, String> validationErrors;
    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(int code, String message, Map<String, String> validationErrors) {
        super();
        this.code = code;
        this.message = message;
        this.validationErrors = validationErrors;
    }
}