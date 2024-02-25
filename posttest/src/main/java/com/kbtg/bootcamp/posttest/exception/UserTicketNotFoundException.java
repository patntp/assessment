package com.kbtg.bootcamp.posttest.exception;

public class UserTicketNotFoundException extends RuntimeException {
    public UserTicketNotFoundException(String message) {
        super(message);
    }
}
