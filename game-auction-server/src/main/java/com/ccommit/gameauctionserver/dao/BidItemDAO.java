package com.ccommit.gameauctionserver.dao;

import com.ccommit.gameauctionserver.dto.Bid;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class BidItemDAO {

    private final RedisTemplate<String, Bid> redisTemplate;
    private final BidMapper bidMapper;
    private static final String bidKey = ":BID";


    private String getTime() {
        return String.format("%02d", LocalTime.now().getHour());
    }

    private String generateBidKey(int id) {
        return getTime() + ":" + id + bidKey;
    }

    public Bid readBidWithCache(int bidId) {
        String key = generateBidKey(bidId);
        String cacheKey = readCacheKey(bidId);

        if (cacheKey != null) {
            Bid cacheBid = redisTemplate.opsForValue().get(cacheKey);
            return cacheBid;
        } else {
            Bid bid = bidMapper.readBidWithItemID(bidId);
            if (bid != null) {
                redisTemplate.opsForValue().set(key, bid);
                redisTemplate.expire(key, 1, TimeUnit.DAYS);
            }
            return bid;
        }
    }

    public Bid UpdateCacheData(Bid bid) {
        String key = readCacheKey(bid.getId());

        redisTemplate.opsForValue().set(key,bid);

        return bid;
    }

    /**
     * Redis는 단일쓰레드를 사용하고 있어서
     * redisTemplate.keys를 사용하여 key값을 탐색할 경우 시간복잡도 O(n)을 가지고 있습니다.
     * 또한 탐색을 하는 경우에도 다른 작업들이 블로킹이되어 성능이 저하됩니다.
     * 현재 key값은 Time:BidID:TableName 형태를 가지고있으며, BidID에 대한 중첩된 캐싱 데이터를 제한하며
     * 성능 향상 및 블로킹 관련 문제를 해결하기위해 ScanOptions를 사용하였습니다.
     */

    private String readCacheKey(int bidId) {
        String matchKey = bidId + bidKey;
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*:" + matchKey)
                .count(50)
                .build();

        Cursor<String> cursor = redisTemplate.scan(scanOptions);

        if (cursor.hasNext()) {
            String key = cursor.next();
            cursor.close();
            return key;
        } else {
            cursor.close();
            return null;
        }
    }

    public List<Bid> readSoldDataWithCache(){
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*" + bidKey)
                .count(50)
                .build();

        Cursor<String> cursor = redisTemplate.scan(scanOptions);

        List<Bid> bidList = new ArrayList<>();

        while (cursor.hasNext())
        {
            String key = cursor.next();
            Bid bid = redisTemplate.opsForValue().get(key);
            if(!bid.isSold())
            {
                continue;
            }
            bidList.add(bid);
        }
        cursor.close();
        return bidList;
    }

    public List<Bid> readCacheDataWithTime(){
        String matchTime = getTime();
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(matchTime+":*")
                .count(50)
                .build();

        Cursor<String> cursor = redisTemplate.scan(scanOptions);
        List<Bid> bidList = new ArrayList<>();
        while(cursor.hasNext())
        {
            String key = cursor.next();
            bidList.add(redisTemplate.opsForValue().get(key));
        }
        cursor.close();
        return bidList;
    }

    public Map<String,Bid> readCacheData(){
        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match("*" + bidKey)
                .count(50)
                .build();

        Cursor<String> cursor = redisTemplate.scan(scanOptions);

        Map<String,Bid> bidMap = new HashMap<>();

        while (cursor.hasNext())
        {
            String key = cursor.next();
            Bid bid = redisTemplate.opsForValue().get(key);
            if(!bid.isSold())
            {
                continue;
            }
            bidMap.put(key,bid);
        }
        cursor.close();
        return bidMap;
    }

    public void deleteCacheData(){
        //redisTemplate.delete()
    }
}