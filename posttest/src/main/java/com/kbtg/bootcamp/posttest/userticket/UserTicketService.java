package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.exception.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserTicketService {
    private final UserTicketRepository userTicketRepository;
    private final LotteryService lotteryService;

    public UserTicketService(UserTicketRepository userTicketRepository, LotteryService lotteryService) {
        this.userTicketRepository = userTicketRepository;
        this.lotteryService = lotteryService;
    }

    @Transactional
    public BuyLotteryResponseDto buyLottery(String userId, String ticketId) throws Exception{
        Optional<Lottery> optionalLottery = lotteryService.getLottery(ticketId);
        UserTicket userTicket;
        if (optionalLottery.isPresent()) {
            userTicket = new UserTicket();
            userTicket.setUserId(userId);
            userTicket.setTicketId(ticketId);
            userTicketRepository.save(userTicket);
            lotteryService.updateLotteryAmount(ticketId);
            return new BuyLotteryResponseDto(userTicket.getId());
        } else {
            throw new LotteryNotFoundException("There is no lottery with ticket id: " + ticketId);
        }
    }
}
