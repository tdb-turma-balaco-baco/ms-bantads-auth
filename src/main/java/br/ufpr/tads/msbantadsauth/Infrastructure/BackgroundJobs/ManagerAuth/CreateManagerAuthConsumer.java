package br.ufpr.tads.msbantadsauth.Infrastructure.BackgroundJobs.ManagerAuth;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.IManagerAuth;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.RemoveManagerAuthEvent;

@Component
@RabbitListener(queues = {"${auth.manager.queue}"})
public class CreateManagerAuthConsumer {
    
    @Autowired
    IManagerAuth _managerAuth;
    
    @RabbitHandler
    public void receive(@Payload CreateManagerAuthEvent event){
        _managerAuth.createManagerAuth(event);
    }

    @RabbitHandler
    public void receiveDelete(@Payload RemoveManagerAuthEvent event){
        _managerAuth.removeManagerAuth(event);
    }
}
