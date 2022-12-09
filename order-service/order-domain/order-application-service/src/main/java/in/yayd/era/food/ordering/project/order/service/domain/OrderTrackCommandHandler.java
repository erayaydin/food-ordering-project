package in.yayd.era.food.ordering.project.order.service.domain;

import in.yayd.era.food.ordering.project.order.service.domain.dto.track.TrackOrderQuery;
import in.yayd.era.food.ordering.project.order.service.domain.dto.track.TrackOrderResponse;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Order;
import in.yayd.era.food.ordering.project.order.service.domain.exception.OrderNotFoundException;
import in.yayd.era.food.ordering.project.order.service.domain.mapper.OrderDataMapper;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.OrderRepository;
import in.yayd.era.food.ordering.project.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> maybeOrder = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));

        if (maybeOrder.isEmpty()) {
            log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id: " + trackOrderQuery.getOrderTrackingId());
        }

        return orderDataMapper.orderToTrackOrderResponse(maybeOrder.get());
    }

}
