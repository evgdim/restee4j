package com.github.evgdim.exception;

public class RestException extends Exception{
    private Short status;
    public RestException(String message, Short status) {
        super(message);
        this.status = status;
    }
}
