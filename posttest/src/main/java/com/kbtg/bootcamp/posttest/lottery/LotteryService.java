package com.kbtg.bootcamp.posttest.lottery;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    public LotteryResponseDto createLottery(LotteryRequestDto requestDto){
        Optional<Lottery> optionalLottery = lotteryRepository.findById(requestDto.getTicket());
        Lottery lottery;
        if (optionalLottery.isPresent()) {
            lottery = optionalLottery.get();
            lottery.setAmount(lottery.getAmount() + requestDto.getAmount());
        } else {
            lottery = new Lottery();
            lottery.setTicket(requestDto.getTicket());
            lottery.setPrice(requestDto.getPrice());
            lottery.setAmount(requestDto.getAmount());
        }

        lotteryRepository.save(lottery);
        return new LotteryResponseDto(lottery.getTicket());
    }
}
