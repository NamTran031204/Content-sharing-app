package com.app.csapp.exceptions;

public class SamePasswordException extends RuntimeException{
    public SamePasswordException(String message){
        super(message);
    }
}
