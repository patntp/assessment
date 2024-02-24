package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.PriceConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
}