package br.ufpr.tads.msbantadsauth.Infrastructure.Services.Messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Messaging.IMessageSender;
import br.ufpr.tads.msbantadsauth.Domain.Events.Common.DomainEvent;

@Component
public class MessageSender implements IMessageSender{
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queueProducer;

    @Override
    public void sendMessage(DomainEvent event) {
        rabbitTemplate.convertAndSend(this.queueProducer.getName(), event);
    }
}
