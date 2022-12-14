package in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository;

import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Order;
import in.yayd.era.food.ordering.project.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId orderId);

    Optional<Order> findByTrackingId(TrackingId trackingId);

}
