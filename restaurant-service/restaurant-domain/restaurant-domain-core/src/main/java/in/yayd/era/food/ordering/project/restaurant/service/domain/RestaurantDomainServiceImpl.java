package in.yayd.era.food.ordering.project.restaurant.service.domain;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.domain.valueobject.OrderApprovalStatus;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Restaurant;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovalEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovedEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderRejectedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static in.yayd.era.food.ordering.project.domain.DomainConstants.DefaultTimezone;

@Slf4j
public class RestaurantDomainServiceImpl implements RestaurantDomainService {
    @Override
    public OrderApprovalEvent validateOrder(Restaurant restaurant, List<String> failureMessages) {
        restaurant.validateOrder(failureMessages);
        log.info("Validating order with id: {}", restaurant.getOrderDetail().getId().getValue());

        if (failureMessages.isEmpty()) {
            log.info("Order is approved for order id: {}", restaurant.getOrderDetail().getId().getValue());
            restaurant.constructOrderApproval(OrderApprovalStatus.APPROVED);
            return new OrderApprovedEvent(restaurant.getOrderApproval(), restaurant.getId(), failureMessages, ZonedDateTime.now(ZoneId.of(DefaultTimezone)));
        }

        log.info("Order is rejected for order id: {}", restaurant.getOrderDetail().getId().getValue());
        restaurant.constructOrderApproval(OrderApprovalStatus.REJECTED);
        return new OrderRejectedEvent(restaurant.getOrderApproval(), restaurant.getId(), failureMessages, ZonedDateTime.now(ZoneId.of(DefaultTimezone)));
    }
}
