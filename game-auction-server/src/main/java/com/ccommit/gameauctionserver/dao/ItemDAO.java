package com.ccommit.gameauctionserver.dao;

import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class ItemDAO {

    private final RedisTemplate<String, Item> redisTemplate;
    private final ItemMapper itemMapper;
    private final String itemKey = ":ITEM";

    private String generateItemKey(int id)
    {
        return id+itemKey;
    }

    public Item readItemWithCache(int itemId)
    {
        String key = generateItemKey(itemId);

        Item item = redisTemplate.opsForValue().get(key);
        if(item != null)
        {
            return item;
        } else {
            item = itemMapper.readItem(itemId);

            redisTemplate.opsForValue().set(key,item);
            redisTemplate.expire(key, 1, TimeUnit.DAYS);

            return item;
        }
        
    }
}
