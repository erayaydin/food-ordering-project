package in.yayd.era.food.ordering.project.order.service.data.access.order.adapter;

import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.order.service.data.access.order.mapper.OrderDataAccessMapper;
import in.yayd.era.food.ordering.project.order.service.data.access.order.repository.OrderJpaRepository;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Order;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.OrderRepository;
import in.yayd.era.food.ordering.project.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository, OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper.orderEntityToOrder(
                orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order))
        );
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return orderJpaRepository.findById(orderId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }

    @Override
    public Optional<Order> findByTrackingId(TrackingId trackingId) {
        return orderJpaRepository.findByTrackingId(trackingId.getValue()).map(orderDataAccessMapper::orderEntityToOrder);
    }
}
