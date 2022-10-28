package com.springboot.miniecommercewebapp.jwtUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.miniecommercewebapp.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        if(e instanceof BadCredentialsException) {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponse invalidUserNameOrPassword = new ErrorResponse(101, "Incorrect account or password");
            httpServletResponse.getOutputStream().write(new ObjectMapper()
                    .writeValueAsBytes(invalidUserNameOrPassword));
        }
        else {
            httpServletResponse.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    e.getMessage()
            );
        }
    }
}