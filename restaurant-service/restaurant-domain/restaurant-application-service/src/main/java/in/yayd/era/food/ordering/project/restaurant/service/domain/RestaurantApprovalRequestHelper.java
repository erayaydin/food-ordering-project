package in.yayd.era.food.ordering.project.restaurant.service.domain;

import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.restaurant.service.domain.dto.RestaurantApprovalRequest;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Restaurant;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovalEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.exception.RestaurantNotFoundException;
import in.yayd.era.food.ordering.project.restaurant.service.domain.mapper.RestaurantDataMapper;
import in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.message.publisher.OrderApprovedMessagePublisher;
import in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.message.publisher.OrderRejectedMessagePublisher;
import in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.repository.OrderApprovalRepository;
import in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class RestaurantApprovalRequestHelper {

    private final RestaurantDomainService restaurantDomainService;
    private final RestaurantDataMapper restaurantDataMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderApprovalRepository orderApprovalRepository;
    private final OrderApprovedMessagePublisher orderApprovedMessagePublisher;
    private final OrderRejectedMessagePublisher orderRejectedMessagePublisher;

    public RestaurantApprovalRequestHelper(
            RestaurantDomainService restaurantDomainService,
            RestaurantDataMapper restaurantDataMapper,
            RestaurantRepository restaurantRepository,
            OrderApprovalRepository orderApprovalRepository,
            OrderApprovedMessagePublisher orderApprovedMessagePublisher,
            OrderRejectedMessagePublisher orderRejectedMessagePublisher
    ) {
        this.restaurantDomainService = restaurantDomainService;
        this.restaurantDataMapper = restaurantDataMapper;
        this.restaurantRepository = restaurantRepository;
        this.orderApprovalRepository = orderApprovalRepository;
        this.orderApprovedMessagePublisher = orderApprovedMessagePublisher;
        this.orderRejectedMessagePublisher = orderRejectedMessagePublisher;
    }

    @Transactional
    public OrderApprovalEvent persistOrderApproval(RestaurantApprovalRequest restaurantApprovalRequest) {
        log.info("Processing restaurant approval for order id: {}", restaurantApprovalRequest.getOrderId());
        List<String> failureMessages = new ArrayList<>();
        Restaurant restaurant = findRestaurant(restaurantApprovalRequest);
        OrderApprovalEvent orderApprovalEvent = restaurantDomainService.validateOrder(restaurant, failureMessages, orderApprovedMessagePublisher, orderRejectedMessagePublisher);
        orderApprovalRepository.save(restaurant.getOrderApproval());
        return orderApprovalEvent;
    }

    private Restaurant findRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        Restaurant restaurant = restaurantDataMapper.restaurantApprovalRequestToRestaurant(restaurantApprovalRequest);
        Optional<Restaurant> maybeRestaurant = restaurantRepository.findRestaurantInformation(restaurant);

        if (maybeRestaurant.isEmpty()) {
            log.error("Restaurant with id " + restaurant.getId().getValue() + " not found!");
            throw new RestaurantNotFoundException("Restaurant with id " + restaurant.getId().getValue() + " not found!");
        }

        Restaurant restaurantEntity = maybeRestaurant.get();
        restaurant.setActive(restaurantEntity.isActive());
        restaurant.getOrderDetail().getProducts().forEach(product -> restaurantEntity.getOrderDetail().getProducts().forEach(p -> {
            if (p.getId().equals(product.getId())) {
                product.updateWithConfirmedNamePriceAndAvailability(p.getName(), p.getPrice(), p.isAvailable());
            }
        }));
        restaurant.getOrderDetail().setId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())));

        return restaurant;
    }
}
