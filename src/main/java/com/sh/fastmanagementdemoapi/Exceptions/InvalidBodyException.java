package com.sh.fastmanagementdemoapi.Exceptions;

public class InvalidBodyException extends RuntimeException {
    public InvalidBodyException(String message) {
        super(message);
    }
}
