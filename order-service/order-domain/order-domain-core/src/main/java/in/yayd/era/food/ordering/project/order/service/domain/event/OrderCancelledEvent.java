package in.yayd.era.food.ordering.project.order.service.domain.event;

import in.yayd.era.food.ordering.project.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent {
    public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
