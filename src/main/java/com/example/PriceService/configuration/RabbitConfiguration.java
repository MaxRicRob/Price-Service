package com.example.PriceService.configuration;

import com.example.PriceService.api.RabbitController;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    @Value("${xchange.name}")
    private String directXchangeName;

    @Value("${routing-keys.price-service}")
    private String priceServiceRoutingKey;

    @Value("${queue-names.price-service}")
    private String priceServiceQueueName;


    @Bean
    public RabbitController rabbitController() {
        return new RabbitController();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }

    @Bean
    public Queue priceServiceQueue() {
        return new Queue(priceServiceQueueName);
    }

    @Bean
    public Binding priceServiceBinding(DirectExchange directExchange, Queue priceServiceQueue) {
        return BindingBuilder.bind(priceServiceQueue).to(directExchange).with(priceServiceRoutingKey);
    }


}
