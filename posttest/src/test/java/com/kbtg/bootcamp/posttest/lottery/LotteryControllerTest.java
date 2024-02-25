package com.kbtg.bootcamp.posttest.lottery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LotteryControllerTest {

    MockMvc mockMvc;
    @Mock
    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        LotteryController lotteryController = new LotteryController(lotteryService);
        mockMvc = MockMvcBuilders.standaloneSetup(lotteryController)
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("when get lottery list on GET: /lotteries should return status 200 and body contain tickets")
    void getLotteryList() throws Exception {
        Lottery lottery = new Lottery();
        lottery.setTicket("000001");
        lottery.setPrice(81);
        lottery.setAmount(2);

        Lottery lottery2 = new Lottery();
        lottery2.setTicket("000002");
        lottery2.setPrice(85);
        lottery2.setAmount(3);

        Lottery lottery3 = new Lottery();
        lottery3.setTicket("123456");
        lottery3.setPrice(80);
        lottery3.setAmount(1);

        List<String> ticketList = List.of(lottery.getTicket(), lottery2.getTicket(), lottery3.getTicket());
        when(lotteryService.getLotteryList()).thenReturn(new LotteryListResponseDto(ticketList));

        mockMvc.perform(get("/lotteries"))
                .andExpect(jsonPath("$.tickets[0]", is("000001")))
                .andExpect(jsonPath("$.tickets[1]", is("000002")))
                .andExpect(jsonPath("$.tickets[2]", is("123456")))
                .andExpect(status().isOk());
    }
}