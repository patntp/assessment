package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.exception.UserTicketNotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTicketServiceTest {

    @Mock
    UserTicketRepository userTicketRepository;
    @Mock
    LotteryService lotteryService;

    UserTicketService userTicketService;

    @BeforeEach
    void setUp() {
        userTicketService = new UserTicketService(userTicketRepository, lotteryService);
    }

    @Test
    @DisplayName("buy a lottery successfully and verify that save method in LotteryRepository is called")
    void buyLottery() throws Exception {
        String userId = "1234567890";
        String ticketId = "123456";

        Lottery lottery = new Lottery();
        lottery.setTicket(ticketId);
        lottery.setPrice(80);
        lottery.setAmount(1);

        when(lotteryService.getLottery(ticketId)).thenReturn(Optional.of(lottery));

        BuyLotteryResponseDto responseDto = userTicketService.buyLottery(userId, ticketId);

        verify(userTicketRepository).save(Mockito.any(UserTicket.class));
    }

    @Test
    @DisplayName("list user lotteries and get count and cost")
    void listUserLotteries() {
        String userId = "1234567890";

        UserTicket userTicket = new UserTicket();
        userTicket.setTicketId("000001");
        UserTicket userTicket2 = new UserTicket();
        userTicket2.setTicketId("000002");

        Lottery lottery = new Lottery();
        lottery.setTicket("000001");
        lottery.setPrice(80);
        lottery.setAmount(1);

        Lottery lottery2 = new Lottery();
        lottery2.setTicket("000002");
        lottery2.setPrice(80);
        lottery2.setAmount(1);

        List<UserTicket> userTicketList = List.of(userTicket, userTicket2);
        when(userTicketRepository.findAllByUserId(userId)).thenReturn(userTicketList);
        when(lotteryService.getLottery("000001")).thenReturn(Optional.of(lottery));
        when(lotteryService.getLottery("000002")).thenReturn(Optional.of(lottery2));

        ListUserLotteriesResponseDto responseDto = userTicketService.listUserLotteries(userId);

        assertEquals(2, responseDto.getCount());
        assertEquals(160, responseDto.getCost());
    }

    @Test
    @DisplayName("sell user's lottery successfully and get ticket id")
    void sellLottery() {
        String userId = "1234567890";
        String ticketId = "123456";

        when(userTicketRepository.findByUserIdAndTicketId(userId, ticketId)).thenReturn(Optional.of(new UserTicket()));

        LotteryResponseDto responseDto = userTicketService.sellLottery(userId, ticketId);

        verify(userTicketRepository, times(1)).deleteByUserIdAndTicketId(userId, ticketId);
        assertEquals(ticketId, responseDto.getTicket());
    }

    @Test
    @DisplayName("sell user's lottery successfully and get ticket id")
    void sellLotteryNotFound() {
        String userId = "1234567890";
        String ticketId = "123456";

        when(userTicketRepository.findByUserIdAndTicketId(userId, ticketId)).thenReturn(Optional.empty());

        assertThrows(UserTicketNotFoundException.class, () -> userTicketService.sellLottery(userId, ticketId));
    }
}