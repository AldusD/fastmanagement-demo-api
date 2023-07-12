package com.sh.fastmanagementdemoapi.Exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException (String message) {
        super(message);
    }
}
