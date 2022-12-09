package in.yayd.era.food.ordering.project.payment.service.domain;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditEntry;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditHistory;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.Payment;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentCancelledEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentCompletedEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentFailedEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(
            Payment payment,
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages,
            DomainEventPublisher<PaymentCompletedEvent> paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

    PaymentEvent validateAndCancelPayment(
            Payment payment,
            CreditEntry creditEntry,
            List<CreditHistory> creditHistories,
            List<String> failureMessages,
            DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

}
