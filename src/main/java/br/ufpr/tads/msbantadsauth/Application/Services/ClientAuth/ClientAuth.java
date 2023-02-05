package br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth;

import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Messaging.IMessageSender;
import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.CreateClientAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.GeneratePasswordEvent;
import br.ufpr.tads.msbantadsauth.Domain.Entities.User;
import br.ufpr.tads.msbantadsauth.Domain.Enums.UserType;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.AuthCanceledEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordFailEvent;
import br.ufpr.tads.msbantadsauth.Infrastructure.Persistence.UserRepository;

@Service
public class ClientAuth implements IClientAuth {

    @Autowired
    IPasswordManager _passwordManager;

    @Autowired
    IMessageSender _messageSender;

    @Autowired
    UserRepository _userRepository;

    @Override
    public void createClientAuth(CreateClientAuthEvent event) {

        try {
            
            User authEntity = new User(
                    event.getCpf(),
                    event.getEmail(),
                    event.getName(),
                    "",
                    Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo")).getTime(),
                    true,
                    UserType.CLIENT);

            _userRepository.save(authEntity);

        } catch (Exception ex) {

            AuthCanceledEvent authCanceledEvent = new AuthCanceledEvent(
                    event.getCpf(),
                    event.getEmail());

            _messageSender.sendMessage(authCanceledEvent);
        }
    }

    @Override
    public void generatePassword(GeneratePasswordEvent event) {

        try {
            User user = _userRepository.findUserByCPF(event.getCpf());

            String createdPassword = _passwordManager.generatePassword();
            String encryptedPassword = _passwordManager.encryptPassword(createdPassword);

            user.setBlocked(false);
            user.setPassword(encryptedPassword);

            _userRepository.save(user);

            ClientPasswordCreatedEvent responseEvent = new ClientPasswordCreatedEvent(event.getCpf(), user.getEmail(),
                    createdPassword, user.getName());

            _messageSender.sendMessage(responseEvent);
        } catch (Exception ex) {
            ClientPasswordFailEvent failEvent = new ClientPasswordFailEvent(event.getCpf());
            _messageSender.sendMessage(failEvent);
        }
    }
}
