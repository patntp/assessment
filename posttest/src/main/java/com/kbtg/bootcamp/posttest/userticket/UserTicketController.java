package com.kbtg.bootcamp.posttest.userticket;

import com.kbtg.bootcamp.posttest.lottery.LotteryResponseDto;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserTicketController {

    private final UserTicketService userTicketService;

    public UserTicketController(UserTicketService userTicketService) {
        this.userTicketService = userTicketService;
    }

    @PostMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<BuyLotteryResponseDto> buyLottery(@PathVariable @Size(min = 10, max = 10) String userId, @PathVariable @Size(min = 6, max = 6) String ticketId) throws Exception {
        return new ResponseEntity<>(userTicketService.buyLottery(userId, ticketId), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/lotteries")
    public ResponseEntity<ListUserLotteriesResponseDto> listUserLotteries(@PathVariable @Size(min = 10, max = 10) String userId) {
        return new ResponseEntity<>(userTicketService.listUserLotteries(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public ResponseEntity<LotteryResponseDto> sellLottery(@PathVariable @Size(min = 10, max = 10) String userId, @PathVariable @Size(min = 6, max = 6) String ticketId) {
        return new ResponseEntity<>(userTicketService.sellLottery(userId, ticketId), HttpStatus.OK);
    }


}
