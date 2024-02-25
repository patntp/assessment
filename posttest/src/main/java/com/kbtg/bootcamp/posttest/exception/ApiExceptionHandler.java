package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ PriceConflictException.class })
    public ResponseEntity<Object> handleNegativeNumberException(PriceConflictException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ LotteryNotFoundException.class })
    public ResponseEntity<Object> handleLotteryNotFoundException(LotteryNotFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
