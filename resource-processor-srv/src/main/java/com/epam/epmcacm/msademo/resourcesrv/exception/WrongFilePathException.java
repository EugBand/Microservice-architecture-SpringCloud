package com.epam.epmcacm.msademo.resourcesrv.exception;

public class WrongFilePathException extends RuntimeException{

    public WrongFilePathException(String message) {
        super(message);
    }

    public WrongFilePathException(String message, Throwable cause) {
        super(message, cause);
    }
}
