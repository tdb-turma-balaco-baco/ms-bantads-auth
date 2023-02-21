package br.ufpr.tads.msbantadsauth.Infrastructure.Services.Messaging.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.CreateClientAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.GeneratePasswordEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.RemoveManagerAuthEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.AuthCanceledEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordFailEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthFail;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.RemovedManagerAccessEvent;

@Configuration
public class RabbitConfig {
    @Value("${auth.queue.producer}")
    private String producerQueue;

    @Value("${auth.client.queue}")
    private String clientQueue;

    @Value("${auth.manager.queue}")
    private String managerQueue;

    @Bean
    public Queue queueProducer() {
        return new Queue(producerQueue, true);
    }

    @Bean
    public Queue queueClient() {
        return new Queue(clientQueue, true);
    }

    @Bean
    public Queue queueManager() {
        return new Queue(managerQueue, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper());
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setIdClassMapping(customClassMapping());
        return classMapper;
    }

    public Map<String, Class<?>> customClassMapping(){
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("CreateClientAuthEvent", CreateClientAuthEvent.class);
        idClassMapping.put("CreateManagerAuthEvent", CreateManagerAuthEvent.class);
        idClassMapping.put("GeneratePasswordEvent", GeneratePasswordEvent.class);
        idClassMapping.put("RemoveManagerCredentials", RemoveManagerAuthEvent.class);
        
        idClassMapping.put("AuthCanceledEvent", AuthCanceledEvent.class);
        idClassMapping.put("ClientPasswordCreatedEvent", ClientPasswordCreatedEvent.class);
        idClassMapping.put("ClientPasswordFailEvent", ClientPasswordFailEvent.class);
        idClassMapping.put("ManagerAuthCreatedEvent", ManagerAuthCreatedEvent.class);
        idClassMapping.put("ManagerAuthFail", ManagerAuthFail.class);
        idClassMapping.put("RemovedManagerAccessEvent", RemovedManagerAccessEvent.class);
        return idClassMapping;
    }


    public AmqpTemplate template(ConnectionFactory connection) {
        RabbitTemplate template = new RabbitTemplate(connection);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
