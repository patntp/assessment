package com.kbtg.bootcamp.posttest.lottery;

import java.util.List;

public class LotteryListResponseDto {
    private List<String> tickets;

    public LotteryListResponseDto(List<String> tickets) {
        this.tickets = tickets;
    }

    public List<String> getTickets() {
        return tickets;
    }

}
