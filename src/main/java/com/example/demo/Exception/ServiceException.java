package com.example.demo.Exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
