package com.springboot.miniecommercewebapp.jwtUtils;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springboot.miniecommercewebapp.dto.response.AuthenResponse;
import com.springboot.miniecommercewebapp.dto.response.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAccessDenied implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // TODO Auto-generated method stub
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        AuthenResponse authenResponse = new AuthenResponse(403, "Access Denied",
                accessDeniedException.getMessage(), request.getServletPath());
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), authenResponse);

    }

}