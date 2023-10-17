package com.ccommit.gameauctionserver.scheduler;

import com.ccommit.gameauctionserver.dao.BidItemDAO;
import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import com.ccommit.gameauctionserver.mapper.SchedulingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BidScheduler {

    private final BidItemDAO bidItemDAO;
    private final SchedulingMapper schedulingMapper;

    /**
     * @Scheduled : 정해진 시간마다 반복된 처리를 하기위한 어노테이션
     * cron : 스케줄링이 어떤 주기마다 실행하는지에 대한 필드값 6개를 가지고 있습니다.
     * "초 분 시 일 월 요일"
     * "*" : 모든 조건(All)
     * "?" : 설정값이 없을때 (날짜와 요일에만 사용가능)
     * "-" : 범위값을 지정해서 사용하고 싶을 때
     * "/" : 초기값과 증가치를 설정할 때
     * "L" : 지정할 수 있는 마지막 값을 설정할 때 (날짜와 요일에만 사용가능)
     * "W" : 가장 가까운 평일에 (일 에서만 사용 가능)
    */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void updateItemWithBid()
    {
        List<Bid> bidItems = bidItemDAO.readCacheDataWithTime();

        for(Bid bidItem : bidItems)
        {
            schedulingMapper.schedulingUpdateBidInfo(bidItem);
        }
    }

    @Scheduled(cron = "0 0 0/6 * * *")
    public void checkEndTimeWithBid()
    {
        schedulingMapper.schedulingEndBid();
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void deleteItemWithBid()
    {
        schedulingMapper.schedulingDelete();
    }

}
