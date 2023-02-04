package br.ufpr.tads.msbantadsauth.Infrastructure.BackgroundJobs.ClientAuth;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.IClientAuth;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.GeneratePasswordEvent;

@Component
@RabbitListener(queues = {"${auth.queue.consumer}"})
public class GenerateClientPasswordConsumer {
    
    @Autowired
    IClientAuth _clientAuth;
    
    @RabbitHandler
    public void receive(@Payload GeneratePasswordEvent event){
        _clientAuth.generatePassword(event);
    }

}
