package in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.message.publisher;

import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.restaurant.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface RestaurantApprovalResponseMessagePublisher {

    void publish(
            OrderOutboxMessage orderOutboxMessage,
            BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback
    );

}
