package br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpr.tads.msbantadsauth.Application.Abstractions.Messaging.IMessageSender;
import br.ufpr.tads.msbantadsauth.Application.Abstractions.Security.IPasswordManager;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.CreateClientAuthEvent;
import br.ufpr.tads.msbantadsauth.Application.Services.ClientAuth.Events.GeneratePasswordEvent;
import br.ufpr.tads.msbantadsauth.Domain.Entities.User;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.AuthCanceledEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientAuthCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordCreatedEvent;
import br.ufpr.tads.msbantadsauth.Domain.Events.ClientAuth.ClientPasswordFailEvent;

@Service
public class ClientAuth implements IClientAuth {

    @Autowired
    IPasswordManager _passwordManager;

    @Autowired
    IMessageSender _messageSender;

    @Override
    public void createClientAuth(CreateClientAuthEvent event) {

        try {
            Date creationDate = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            User authEntity = new User(
                    event.getCpf(),
                    event.getEmail(),
                    event.getName(),
                    "",
                    creationDate,
                    true,
                    null);

            // Save repo

            ClientAuthCreatedEvent authCreatedEvent = new ClientAuthCreatedEvent();
            _messageSender.sendMessage(authCreatedEvent);

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
            // GET user by cpf

            String emailDoBanco = "";
            // GeneratePassword
            String createdPassword = _passwordManager.generatePassword();

            String encryptedPassword = _passwordManager.encryptPassword(createdPassword);
            // Update user
            //set user Unblocked 

            // create event
            ClientPasswordCreatedEvent responseEvent = new ClientPasswordCreatedEvent(event.getCpf(), emailDoBanco,
                    createdPassword);

            _messageSender.sendMessage(responseEvent);
        } catch (Exception ex) {
            ClientPasswordFailEvent failEvent = new ClientPasswordFailEvent(event.getCpf());
            _messageSender.sendMessage(failEvent);
        }
    }
}
