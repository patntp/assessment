package com.kbtg.bootcamp.posttest.userticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}