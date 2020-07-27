package io.pillopl.testablearch.ex3.application;


import io.pillopl.testablearch.ex3.DomainEventsPublisher;
import io.pillopl.testablearch.ex3.model.CardApplicationRejected;
import io.pillopl.testablearch.ex3.model.CardGranted;
import io.pillopl.testablearch.ex3.model.CreditCard;
import io.pillopl.testablearch.ex3.model.CreditCardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static java.lang.Integer.valueOf;
import static java.util.Optional.of;

/**
 * @author Jakub Pilimon
 */
@Service
public class ApplyForCardService {

    private final DomainEventsPublisher domainEventsPublisher;
    private final CreditCardRepository creditCardRepository;

    public ApplyForCardService(DomainEventsPublisher domainEventsPublisher, CreditCardRepository creditCardRepository) {
        this.domainEventsPublisher = domainEventsPublisher;
        this.creditCardRepository = creditCardRepository;
    }

    @Transactional
    public Optional<CreditCard> apply(String pesel) {
        if (bornBeforeSeventies(pesel)) {
            domainEventsPublisher.publish(new CardApplicationRejected(pesel));
            return Optional.empty();
        }
        CreditCard card = CreditCard.withDefaultLimit(pesel);
        creditCardRepository.save(card);
        domainEventsPublisher.publish(new CardGranted(card.getCardNo(), card.getCardLimit(), pesel));
        return of(card);
    }

    private boolean bornBeforeSeventies(String pesel) {
        return valueOf(pesel.substring(0, 2)) < 70;
    }

}
