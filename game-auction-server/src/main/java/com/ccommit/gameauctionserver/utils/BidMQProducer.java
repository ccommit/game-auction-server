package com.ccommit.gameauctionserver.utils;

import com.ccommit.gameauctionserver.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class BidMQProducer {

    private final String exchange = RabbitMQConfig.exchangeName;
    private final String routing = RabbitMQConfig.routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void ProduceBidData(int userId, int bidId)
    {
        int[] array = {userId,bidId};

        /**
         * RabbitMQConfig에서 빈으로 설정한 RabbitTemplate를 사용하여 메시지를 큐에 전송합니다.
         * RabbitTemplate는 SpringAMCP 프레임워크를 사용하여 고수준으로 사용이 가능한 Produce방법중 하나입니다.
         * Channel은 RabbitTemplate와 비교하여 더 저수준인 접근방식을 가지고 있으며, 예외 처리, 트랜잭션 롤백
         * 및 다양한 설정들이 필요로합니다.
         * RabbitTemplate를 사용한 이유는 Channel을 사용하여 상세한 설정을 사용하지않고
         * 현재 issue/5에서는 Producer와 Receiver사이에 한가지의 큐만 사용하고 있어 RabbitTemplate를 사용하였습니다.
         * */

        rabbitTemplate.convertAndSend(exchange,routing, array);
    }


}
