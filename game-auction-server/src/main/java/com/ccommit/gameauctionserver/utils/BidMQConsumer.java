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
/**
 * @RabbitListner : Attribute Producer에서 전송한 메시지를 queues의 값을 사용하여 이름에 맞는 큐와 매핑하는 역할입니다.
 * RabbitMQConfig에서 설정한 MessageConverter에 따라 현재는 JSON 타입으로 직렬화, 역직렬화를 하게 됩니다.
 * AMQP : AdvancedMessageQueuingProtocol. MessageQueue 기반의 프로토콜로 메시지 관리, 큐잉, 라우팅, 신뢰성, 보안 등에 대해 정의합니다.
 * ACK : Acknowledgment. 메시지를 성공적으로 처리했음을 브로커에게 알리는 메커니즘
 * NACK : Negative ACK. 메시지 처리에 문제가 있음을 나타내는 메커니즘. 메시지를 재전송 혹은 실패 메시지를 처리할 로직을 사용할 때 설정한다.
 * @RabbitListener의 동작순서
 * 1. RabbitMQConfig에서 빈으로 등록한 Queue,Exchange,RoutingKey를 사용하여 RabbitMQ에 메시지를 전송한다(이 때 MessageConverter에 설정한 값에 따라 직렬화를 한다.)
 * 2. RabbitMQ에 전송된 메시지를 queues Attribute를 사용하여 맵핑한 큐와 일치한 메시지를 받아온다.
 * 3. @RabbitListener 메서드의 파라미터 POJO를 역직렬화를 하여 객체 혹은 문자열 등으로 변환하여 받아온다.
 * 4. 전송받은 메시지를 메서드 내의 로직을 실행한다.
 * 5-1. 메시지가 정상적으로 실행되었다면 메시지에 대한 ACK를 자동으로 전송한다. 그 후 메시지 큐에서 해당 메시지를 제거한다.(설정에 따라 ACK를 NACK형태로 변환할수있다.)
 * 5-2. 메시지가 예외가 발생되었다면 SpringAMQP는 해당 예외를 처리하고 RabbitListenerContainerFactory를 빈으로 설정하였다면 해당 설정에 따라 재전송 혹은 예외처리를 실행한다.
* */
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
