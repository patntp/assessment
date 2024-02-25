package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.lottery.LotteryResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserTicketControllerTest {

    MockMvc mockMvc;

    @Mock
    UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        UserTicketController userTicketController = new UserTicketController(userTicketService);
        mockMvc = MockMvcBuilders.standaloneSetup(userTicketController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when buy lottery on POST: /users/:userId/lotteries/:ticketId should return status 201 and body contain ticket")
    void buyLottery() throws Exception{
        String userId = "1234567890";
        String ticketId = "123456";

        when(userTicketService.buyLottery(userId, ticketId)).thenReturn(new BuyLotteryResponseDto(1));

        mockMvc.perform(
                        post("/users/"+ userId + "/lotteries/" + ticketId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    @DisplayName("when list user lotteries on GET: /users/:userId/lotteries should return status 200 and body contain tickets, count, and cost")
    void listUserLotteries() throws Exception {
        String userId = "1234567890";

        List<String> ticketList = List.of("000001", "000002", "123456");
        int count = ticketList.size();
        int cost = 240;

        ListUserLotteriesResponseDto responseDto = new ListUserLotteriesResponseDto(ticketList, count, cost);
        when(userTicketService.listUserLotteries(userId)).thenReturn(new ListUserLotteriesResponseDto(ticketList, count, cost));

        mockMvc.perform(get("/users/"+ userId + "/lotteries/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tickets[0]", is("000001")))
                .andExpect(jsonPath("$.tickets[1]", is("000002")))
                .andExpect(jsonPath("$.tickets[2]", is("123456")))
                .andExpect(jsonPath("$.count", is(ticketList.size())))
                .andExpect(jsonPath("$.cost", is(240)));
    }

    @Test
    @DisplayName("when sell user's lottery on DELETE: /users/:userId/lotteries/:ticketId should return status 200 and body contain ticket")
    void sellLottery() throws Exception {
        String userId = "1234567890";
        String ticketId = "123456";

        when(userTicketService.sellLottery(userId, ticketId)).thenReturn(new LotteryResponseDto(ticketId));

        mockMvc.perform(
                        delete("/users/"+ userId + "/lotteries/" + ticketId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticket", is(ticketId)));
    }
}