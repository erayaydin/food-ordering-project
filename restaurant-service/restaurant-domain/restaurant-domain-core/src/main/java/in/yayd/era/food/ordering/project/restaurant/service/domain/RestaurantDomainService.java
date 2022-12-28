package in.yayd.era.food.ordering.project.restaurant.service.domain;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Restaurant;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovalEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovedEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(
            Restaurant restaurant,
            List<String> failureMessages
    );

}
