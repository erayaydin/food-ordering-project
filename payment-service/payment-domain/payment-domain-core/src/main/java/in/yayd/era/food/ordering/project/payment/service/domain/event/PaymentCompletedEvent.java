package in.yayd.era.food.ordering.project.payment.service.domain.event;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {

    public PaymentCompletedEvent(
            Payment payment,
            ZonedDateTime createdAt
    ) {
        super(payment, createdAt, Collections.emptyList());
    }
}
