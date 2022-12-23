package in.yayd.era.food.ordering.project.payment.service.domain.ports.output.message.publisher;

import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.payment.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface PaymentResponseMessagePublisher {
    void publish(
            OrderOutboxMessage orderOutboxMessage,
            BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback
    );
}
