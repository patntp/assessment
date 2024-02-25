package com.kbtg.bootcamp.posttest.exception;

public class LotteryNotFoundException extends RuntimeException{

    public LotteryNotFoundException(String message){
        super(message);
    }
}
