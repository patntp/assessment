package com.kbtg.bootcamp.posttest.lottery;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class LotteryRequestDto {
    @NotNull
    @Size(min = 6, max = 6, message = "ticket id length should be 6 digits")
    private String ticket;

    @Positive
    private int price;

    @Min(value = 1, message = "amount of tickets to create should be at least 1")
    private int amount;

    public LotteryRequestDto() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}


