package com.ccommit.gameauctionserver.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* Kafka : Message Queue중의 하나로, Cluster내의 Topic을 사용하여 메시지를 처리하는 분산 시스템
* RabbitMQ :  Erlang으로 AMQP를 구현한 메시지 브로커 시스템입니다.
* AMQP : 클라이언트가 메시지 미들웨어 브로커와 통신할 수 있게 해주는 메시지 프로토콜.
* -----------------------------------------------------------------------
*                           Broker
* Producers -> [Exchange -- Binding --> Queue ] -> Consumers
* -----------------------------------------------------------------------
* Producer : 메시지 큐에 메시지를 전송하는 역할.
* Consumer : 메시지 큐에 담긴 메시지를 Wait하여 소비하는역할.
* Exchange : Direct,Topic,Fanout의 형태가 존재하며, Producer가 어디에 보낼것인지에 대한 역할을 담당한다.
* 1. Direct : BindingKey를 사용하여 특정 Queue에 직접 보낸다.
 * - Producer와 Receiver사이에 한가지의 큐만 사용하여 먼저 입찰한 유저의 정보를 저장하기위해 DirectExchange를 사용하였습니다.
* 2. Topic : RoutingPattern과 매칭하여 각각의 패턴에 맞는 Queue와 매칭시켜준다.
* 3. Fanout : 여러개의 Queue와 매칭시켜주는 방식으로, 메시지의 내용을 고려하지않고 모든 바인딩된 Queue에 전송한다.
* Binding : 생성된 Exchange에서 전달받은 메시지를 원하는 Queue로 전달하기 위해 정의하는 규칙
*
* Kafka vs RabbitMQ : https://aws.amazon.com/ko/compare/the-difference-between-rabbitmq-and-kafka/
* */
@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;

    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;

    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;

    public static final String queueName = "Bid_Queue";

    public static final String exchangeName = "Bid_Exchange";

    public static final String routingKey = "Bid_Routing_Key";

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public DirectExchange exchange() {

        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }

    /*
     * setMessageConverter를 사용하여 특정 형식(현재는 JSON)으로 메시지를 직렬화, 역직렬화를 할 수 있도록 설정합니다.
     * The default converter is a SimpleMessageConverter, which is able to handle byte arrays, Strings,
     * and Serializable Objects depending on the message content type header.
     * */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}