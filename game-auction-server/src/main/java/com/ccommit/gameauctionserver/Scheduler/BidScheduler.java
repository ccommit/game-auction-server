package com.ccommit.gameauctionserver.Scheduler;

import com.ccommit.gameauctionserver.service.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@RequiredArgsConstructor
public class BidScheduler {

    private final BidService bidService;


    /*
        cron의 파라미터
        cron = "* * * * * *"
        0. *    : 모든조건을 의미합니다.
        1. 초    (0-59)
        2. 분    (0-59)
        3. 시    (0-23)
        4. 일    (1-31)
        5. 월    (1-12)
        6. 요일(0-7) 0,7은 일요일, 1부터 월요일 ~
    */
    @Scheduled(cron = "0 0/1 * * * *")
    public void checkLimitTime()
    {
        //TODO : Bid Table 로우 삭제 기능 구현
        // 1. limit_time 시간이 현재시간을 지났다면, Bid 테이블 내 해당 로우를 삭제한다.
        // 구현 )         DELETE FROM bid
        //        WHERE limit_time &lt; NOW()
        // 2. limit_time 시간이 현재시간을 초과하였으며, highestbidder_id가 null이 아니라면 트랜잭션을 통해 낙찰 한다.
        // UPDATE item i JOIN user u ON i.user_id = u.id
    }

}
