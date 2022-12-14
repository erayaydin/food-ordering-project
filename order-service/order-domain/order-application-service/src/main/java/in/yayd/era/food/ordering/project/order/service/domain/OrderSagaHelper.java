package in.yayd.era.food.ordering.project.order.service.domain;

import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Order;
import in.yayd.era.food.ordering.project.order.service.domain.exception.OrderNotFoundException;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderSagaHelper {

    private final OrderRepository orderRepository;

    public OrderSagaHelper(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    Order findOrder(String orderId) {
        Optional<Order> maybeOrder = orderRepository.findById(new OrderId(UUID.fromString(orderId)));

        if (maybeOrder.isEmpty()) {
            log.error("Order with id: {} could not be found!", orderId);
            throw new OrderNotFoundException("Order with id " + orderId + " could not be found!");
        }

        return maybeOrder.get();
    }

    void saveOrder(Order order) {
        orderRepository.save(order);
    }

}
