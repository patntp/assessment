package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.PriceConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LotteryServiceTest {

    @Mock
    LotteryRepository lotteryRepository;

    LotteryService lotteryService;

    @BeforeEach
    void setUp() {
        lotteryService = new LotteryService(lotteryRepository);
    }

    @Test
    @DisplayName("create a new lottery successfully and verify that save method in LotteryRepository is called")
    public void createLottery() {
        LotteryRequestDto requestDto = new LotteryRequestDto();
        requestDto.setTicket("123456");
        requestDto.setPrice(80);
        requestDto.setAmount(1);

        when(lotteryRepository.findById(requestDto.getTicket())).thenReturn(Optional.empty());

        LotteryResponseDto responseDto = lotteryService.createLottery(requestDto);

        assertEquals(requestDto.getTicket(), responseDto.getTicket());
        verify(lotteryRepository).save(Mockito.any(Lottery.class));
    }

    @Test
    @DisplayName("create lottery with existing ticket id but different price should throw PriceConflictException")
    void createLotteryThatExistsButDiffPrice() {
        Lottery existingLottery = new Lottery();
        existingLottery.setTicket("123456");
        existingLottery.setPrice(80);
        existingLottery.setAmount(1);

        when(lotteryRepository.findById(existingLottery.getTicket())).thenReturn(Optional.of(existingLottery));

        LotteryRequestDto requestDto = new LotteryRequestDto();
        requestDto.setTicket("123456");
        requestDto.setPrice(90);
        requestDto.setAmount(1);

        assertThrows(PriceConflictException.class, () -> lotteryService.createLottery(requestDto));
    }

    @Test
    @DisplayName("get lottery list should get three tickets")
    void getLotteryList() {
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

        List<Lottery> lotteryList = List.of(lottery, lottery2, lottery3);
        
        when(lotteryRepository.findAll()).thenReturn(lotteryList);

        LotteryListResponseDto responseDto = lotteryService.getLotteryList();

        assertEquals(List.of("000001", "000002", "123456"), responseDto.getTickets());
    }
}