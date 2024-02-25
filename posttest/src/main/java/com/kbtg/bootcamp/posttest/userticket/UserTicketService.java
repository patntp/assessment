package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.exception.LotteryNotFoundException;
import com.kbtg.bootcamp.posttest.exception.UserTicketNotFoundException;
import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public ListUserLotteriesResponseDto listUserLotteries(String userId) {
        List<UserTicket> userTicketList = userTicketRepository.findAllByUserId(userId);

        List<String> ticketList = new ArrayList<>();
        Integer cost = 0;
        Integer count = 0;
        for (UserTicket userTicket: userTicketList) {
            String ticketId = userTicket.getTicketId();
            Optional<Lottery> optionalLottery = lotteryService.getLottery(ticketId);
            Integer ticketPrice = 0;
            if (optionalLottery.isPresent()) {
                ticketPrice = optionalLottery.get().getPrice();
            }
            ticketList.add(ticketId);
            cost = cost + ticketPrice;
            count = count + 1;
        }

        return new ListUserLotteriesResponseDto(ticketList, count, cost);
    }

    @Transactional
    public LotteryResponseDto sellLottery(String userId, String ticketId) {
        Optional<UserTicket> optionalUserTicket = userTicketRepository.findByUserIdAndTicketId(userId, ticketId);
        if (optionalUserTicket.isEmpty()) {
            throw new UserTicketNotFoundException("User ticket not found for userId: " + userId + ", ticketId: " + ticketId);
        }

        userTicketRepository.deleteByUserIdAndTicketId(userId, ticketId);
        return new LotteryResponseDto(ticketId);
    }
}
