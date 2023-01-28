package br.ufpr.tads.msbantadsauth.Infrastructure.BackgroundJobs.ClientAuth;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.IClientAuth;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.CreateClientAuthEvent;

@Component
public class CreateClientAuthConsumer {

    @Autowired
    IClientAuth _clientAuth;
    
    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload CreateClientAuthEvent event){
        
    }
}
