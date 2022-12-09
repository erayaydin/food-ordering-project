package in.yayd.era.food.ordering.project.restaurant.service.domain.ports.input.message.listener;

import in.yayd.era.food.ordering.project.restaurant.service.domain.dto.RestaurantApprovalRequest;

public interface RestaurantApprovalRequestMessageListener {

    void approveOrder(RestaurantApprovalRequest restaurantApprovalRequest);

}
