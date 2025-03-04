package com.nodal.nodal_rest.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String exception){
        super(exception);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
