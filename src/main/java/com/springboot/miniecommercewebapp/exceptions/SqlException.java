package com.springboot.miniecommercewebapp.exceptions;

public class SqlException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public SqlException() {
        super();
    }

    public SqlException(String message) {
        super(message);
    }

    public SqlException(String message, Throwable cause) {
        super(message, cause);
    }
}