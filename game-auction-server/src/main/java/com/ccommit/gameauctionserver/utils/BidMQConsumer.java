package com.ccommit.gameauctionserver.utils;

import com.ccommit.gameauctionserver.config.RabbitMQConfig;
import com.ccommit.gameauctionserver.dto.bid.BidWithUserDTO;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidMQConsumer {

    private final BidMapper bidMapper;

    @RabbitListener(queues = RabbitMQConfig.bidQueueName)
    public void receiveMessageQueueWithBidData(BidWithUserDTO bidWithUserDTO) {

        if (bidWithUserDTO.getPirceGold() == bidWithUserDTO.getBid().getPrice()) {
            bidMapper.updateInstantBid(bidWithUserDTO.getUserInfo().getId(),
                                    bidWithUserDTO.getBid().getId());

        }

        bidMapper.updateUserGold(bidWithUserDTO.getUserInfo().getUserId(),
                -bidWithUserDTO.getPirceGold());

        if(bidWithUserDTO.getBid().getHighestBidderId() != null)
        {
            bidMapper.updateUserGold(bidWithUserDTO.getBid().getHighestBidderId(),
                                    bidWithUserDTO.getBid().getPresentPrice());
        }
    }
}
