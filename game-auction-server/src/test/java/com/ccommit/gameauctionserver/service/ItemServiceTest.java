package com.ccommit.gameauctionserver.service;

import com.ccommit.gameauctionserver.dto.Item;
import com.ccommit.gameauctionserver.dto.item.ItemMainCategory;
import com.ccommit.gameauctionserver.dto.item.ItemRating;
import com.ccommit.gameauctionserver.dto.item.ItemSubCategory;
import com.ccommit.gameauctionserver.mapper.ItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private ItemService itemService;

    private Item item;

    @BeforeEach
    public void CreateItem()
    {
        item = Item.builder()
                .id(1)
                .name("TestItemName")
                .equipmentLevel(100)
                .rating(ItemRating.ANCIENT)
                .mainType(ItemMainCategory.EQUIPMENT)
                .subType(ItemSubCategory.WEAPON)
                .firstProperty(1000)
                .secondProperty(500)
                .itemImageURL("TestItemImageURL")
                .userId(5)
                .exist(true)
                .build();

    }

    @Test
    @DisplayName("아이템 등록 성공")
    public void createItemSuccess()
    {
        doNothing().when(itemMapper).createItem(any(Item.class));

        itemService.createItem(item);

        verify(itemMapper).createItem(any(Item.class));
    }

}
