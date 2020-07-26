package io.pillopl.testablearch.ex4.model;

import java.time.Instant;

/**
 * @author Jakub Pilimon
 */
public class CardApplicationRejected implements DomainEvent {

    final String clientPesel;
    final Instant timestamp = Instant.now();

    public CardApplicationRejected(String clientPesel) {
        this.clientPesel = clientPesel;
    }

    public String getClientPesel() {
        return clientPesel;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String getType() {
        return "card-application-rejected";
    }
}
