package in.yayd.era.food.ordering.project.order.service.domain.ports.output.message.publisher.restaurantapproval;

import in.yayd.era.food.ordering.project.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface RestaurantApprovalRequestMessagePublisher {
    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage, BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
