package br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Messaging.IMessageSender;
import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;
import br.ufpr.tads.msbantadsauth.Domain.Entities.User;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthFail;

@Service
public class ManagerAuth implements IManagerAuth {

    @Autowired
    IPasswordManager _passwordManager;

    @Autowired
    IMessageSender _messageSender;
    
    @Override
    public void createManagerAuth(CreateManagerAuthEvent event) {
        try {

            String createdPassword = _passwordManager.generatePassword();
            String passwordEncrypted = _passwordManager.encryptPassword(createdPassword);

            Date creationDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            User authEntity = new User(
                    event.getCpf(),
                    event.getEmail(),
                    event.getName(),
                    passwordEncrypted,
                    creationDate,
                    false,
                    null);

            // Save repo

            ManagerAuthCreatedEvent authCreatedEvent = new ManagerAuthCreatedEvent(
                event.getCpf(),
                event.getEmail(),
                event.getName(),
                createdPassword
            );
            _messageSender.sendMessage(authCreatedEvent);

        } catch (Exception ex) {

            ManagerAuthFail managerCanceledEvent = new ManagerAuthFail(
                    event.getCpf(),
                    event.getEmail());

            _messageSender.sendMessage(managerCanceledEvent);
        }
    }
    
}
