package in.yayd.era.food.ordering.project.payment.service.domain.event;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.Payment;

import java.time.ZonedDateTime;
import java.util.Collections;

public class PaymentCompletedEvent extends PaymentEvent {

    private final DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher;

    public PaymentCompletedEvent(
            Payment payment,
            ZonedDateTime createdAt,
            DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher
    ) {
        super(payment, createdAt, Collections.emptyList());
        this.paymentCompletedEventDomainEventPublisher = paymentCompletedEventDomainEventPublisher;
    }

    @Override
    public void fire() {
        paymentCompletedEventDomainEventPublisher.publish(this);
    }
}
