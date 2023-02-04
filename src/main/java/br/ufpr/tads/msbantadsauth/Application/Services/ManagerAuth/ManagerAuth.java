package br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Messaging.IMessageSender;
import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;
import br.ufpr.tads.msbantadsauth.Application.Services.ManagerAuth.Events.CreateManagerAuthEvent;
import br.ufpr.tads.msbantadsauth.Domain.Entities.User;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ManagerAuth.ManagerAuthFail;
import br.ufpr.tads.msbantadsauth.Infrastructure.Persistence.UserRepository;

@Service
public class ManagerAuth implements IManagerAuth {

    @Autowired
    IPasswordManager _passwordManager;

    @Autowired
    IMessageSender _messageSender;

    @Autowired
    UserRepository _userRepository;
    
    @Override
    public void createManagerAuth(CreateManagerAuthEvent event) {
        try {

            String createdPassword = _passwordManager.generatePassword();
            String passwordEncrypted = _passwordManager.encryptPassword(createdPassword);

            User authEntity = new User(
                    event.getCpf(),
                    event.getEmail(),
                    event.getName(),
                    passwordEncrypted,
                    Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime(),
                    false,
                    null);

            _userRepository.save(authEntity);

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
