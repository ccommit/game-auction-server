package com.ccommit.gameauctionserver.dao;

import com.ccommit.gameauctionserver.dto.bid.BidWithUserDTO;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class HistoryBidDAO {

    private final RedisTemplate<String, BidWithUserDTO> redisTemplate;
    private final BidMapper bidMapper;
    private static final String exceptionKey = ":BID_EXCEPTION";

    private String generateExceptionKey(int id)
    {
        return id + exceptionKey;
    }
    public void insertHistoryUpdateBid(BidWithUserDTO bidWithUserDTO)
    {
        String key = generateExceptionKey(bidWithUserDTO.getBid().getId());

        BidWithUserDTO cacheData = redisTemplate.opsForValue().get(key);

        if(cacheData == null)
        {
            redisTemplate.opsForValue().set(key,bidWithUserDTO);
            redisTemplate.expire(key, 1, TimeUnit.DAYS);
        }
        else
        {
            if(cacheData.getPirceGold() < bidWithUserDTO.getPirceGold())
            {
                redisTemplate.opsForValue().set(key,bidWithUserDTO);
            }
        }
    }

    public List<BidWithUserDTO> readHistoryDataWithCache()
    {
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*")
                .count(50)
                .build();

        Cursor<String> cursor = redisTemplate.scan(scanOptions);
        List<BidWithUserDTO> bidWithUserDTOList = null;
        while(cursor.hasNext())
        {
            String key = cursor.next();
            bidWithUserDTOList.add(redisTemplate.opsForValue().get(key));
        }
        cursor.close();
        return bidWithUserDTOList;
    }
}
