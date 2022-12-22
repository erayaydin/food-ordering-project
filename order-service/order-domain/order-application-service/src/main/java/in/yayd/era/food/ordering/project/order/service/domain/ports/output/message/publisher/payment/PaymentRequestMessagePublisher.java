package in.yayd.era.food.ordering.project.order.service.domain.ports.output.message.publisher.payment;

import in.yayd.era.food.ordering.project.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {
    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage, BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
