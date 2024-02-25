package com.kbtg.bootcamp.posttest.lottery;

import com.kbtg.bootcamp.posttest.exception.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exception.PriceConflictException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotteryService {
    private final LotteryRepository lotteryRepository;

    public LotteryService(LotteryRepository lotteryRepository) {
        this.lotteryRepository = lotteryRepository;
    }

    @Transactional
    public LotteryResponseDto createLottery(LotteryRequestDto requestDto){
        Optional<Lottery> optionalLottery = lotteryRepository.findById(requestDto.getTicket());
        Lottery lottery;
        if (optionalLottery.isPresent()) {
            lottery = optionalLottery.get();
            if (lottery.getPrice() != requestDto.getPrice()) {
                throw new PriceConflictException("Conflicted prices. Lottery with ticket id: " + lottery.getTicket()
                        + " costs " + lottery.getPrice());
            }
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

    public LotteryListResponseDto getLotteryList() {
        List<Lottery> lotteries = lotteryRepository.findAll();

        List<String> tickets = lotteries.stream()
                .map(Lottery::getTicket)
                .collect(Collectors.toList());

        LotteryListResponseDto responseDto = new LotteryListResponseDto(tickets);

        return responseDto;
    }

    public Optional<Lottery> getLottery(String ticketId){
        return lotteryRepository.findById(ticketId);
    }

    public void updateLotteryAmount(String ticketId){
        Lottery lottery = getLottery(ticketId).get();
        if (lottery.getAmount() > 0) {
            lottery.setAmount(lottery.getAmount() - 1);
        } else {
            throw new LotteryNotFoundException("Lottery with ticket id: " + ticketId + " is sold out.");
        }
        lotteryRepository.save(lottery);
    }
}
