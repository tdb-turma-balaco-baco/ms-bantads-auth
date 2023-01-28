package br.ufpr.tads.msbantadsauth.Application.Abstractions.Messaging;

import br.ufpr.tads.msbantadsauth.Domain.Events.Common.DomainEvent;

public interface IMessageSender {
    void sendMessage(DomainEvent event);
}
