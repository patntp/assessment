package com.kbtg.bootcamp.posttest.userticket;

import java.util.List;

public class ListUserLotteriesResponseDto {
    private List<String> tickets;

    private Integer count;

    private Integer cost;

    public ListUserLotteriesResponseDto(List<String> tickets, Integer count, Integer cost) {
        this.tickets = tickets;
        this.count = count;
        this.cost = cost;
    }

    public List<String> getTickets() {
        return tickets;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getCost() {
        return cost;
    }
}
