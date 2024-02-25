package com.kbtg.bootcamp.posttest.admin;

import com.kbtg.bootcamp.posttest.lottery.Lottery;
import com.kbtg.bootcamp.posttest.lottery.LotteryRequestDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryResponseDto;
import com.kbtg.bootcamp.posttest.lottery.LotteryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LotteryService lotteryService;

    public AdminController(LotteryService lotteryService) {
        this.lotteryService = lotteryService;
    }

    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponseDto> createLottery(@Valid @RequestBody LotteryRequestDto requestDto){
        return new ResponseEntity<>(lotteryService.createLottery(requestDto), HttpStatus.CREATED);
    }
}
