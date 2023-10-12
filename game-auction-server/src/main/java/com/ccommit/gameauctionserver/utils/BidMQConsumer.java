package com.ccommit.gameauctionserver.utils;

import com.ccommit.gameauctionserver.config.RabbitMQConfig;
import com.ccommit.gameauctionserver.mapper.BidMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidMQConsumer {

    private final BidMapper bidMapper;
    private final String queue = RabbitMQConfig.queueName;

    @RabbitListener(queues = queue)
    public void receiveMessageQueueWithBidData(int[] array)
    {
        bidMapper.updateInstantBid(array[0],array[1]);
    }

}
