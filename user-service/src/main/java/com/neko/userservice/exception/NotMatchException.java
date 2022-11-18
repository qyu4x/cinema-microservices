package com.neko.userservice.exception;

public class NotMatchException extends RuntimeException{
    public NotMatchException(String message) {
        super(message);
    }
}
