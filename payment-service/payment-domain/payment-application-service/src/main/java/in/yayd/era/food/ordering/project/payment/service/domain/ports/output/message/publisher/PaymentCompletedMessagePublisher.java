package in.yayd.era.food.ordering.project.payment.service.domain.ports.output.message.publisher;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentCompletedEvent;

public interface PaymentCompletedMessagePublisher extends DomainEventPublisher<PaymentCompletedEvent> {
}
