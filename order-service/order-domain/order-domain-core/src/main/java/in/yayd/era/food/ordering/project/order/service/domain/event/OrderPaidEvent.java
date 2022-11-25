package in.yayd.era.food.ordering.project.order.service.domain.event;

import in.yayd.era.food.ordering.project.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {
    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
