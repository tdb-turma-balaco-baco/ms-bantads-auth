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
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.AuthCanceledEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordFailEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthFail;

@Configuration
public class SenderConfig {
    @Value("${auth.queue.producer}")
    private String message;

    @Bean
    public Queue queue() {
        return new Queue(message, true);
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
        
        idClassMapping.put("AuthCanceledEvent", AuthCanceledEvent.class);
        idClassMapping.put("ClientPasswordCreatedEvent", ClientPasswordCreatedEvent.class);
        idClassMapping.put("ClientPasswordFailEvent", ClientPasswordFailEvent.class);
        idClassMapping.put("ManagerAuthCreatedEvent", ManagerAuthCreatedEvent.class);
        idClassMapping.put("ManagerAuthFail", ManagerAuthFail.class);
        return idClassMapping;
    }


    public AmqpTemplate template(ConnectionFactory connection) {
        RabbitTemplate template = new RabbitTemplate(connection);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
