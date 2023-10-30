package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    public void createItem(Item item)
    {
        itemMapper.createItem(item);
    }
}
