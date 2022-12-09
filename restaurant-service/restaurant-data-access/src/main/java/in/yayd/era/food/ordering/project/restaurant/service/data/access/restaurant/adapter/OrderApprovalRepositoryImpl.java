package in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.adapter;

import in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.mapper.RestaurantDataAccessMapper;
import in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.repository.OrderApprovalJpaRepository;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.OrderApproval;
import in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderApprovalRepositoryImpl implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public OrderApprovalRepositoryImpl(OrderApprovalJpaRepository orderApprovalJpaRepository, RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.orderApprovalJpaRepository = orderApprovalJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        return restaurantDataAccessMapper
                .orderApprovalEntityToOrderApproval(
                        orderApprovalJpaRepository.save(
                                restaurantDataAccessMapper.orderApprovalToOrderApprovalEntity(orderApproval)
                        )
                );
    }
}
