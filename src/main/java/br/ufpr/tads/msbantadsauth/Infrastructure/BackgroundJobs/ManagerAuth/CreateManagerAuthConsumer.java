package br.ufpr.tads.msbantadsauth.Infrastructure.BackgroundJobs.ManagerAuth;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.IManagerAuth;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;

@Component
public class CreateManagerAuthConsumer {
    
    @Autowired
    IManagerAuth _managerAuth;
    
    @RabbitListener(queues = {"${auth.queue.consumer}"})
    public void receive(@Payload CreateManagerAuthEvent event){
        _managerAuth.createManagerAuth(event);
    }
}
