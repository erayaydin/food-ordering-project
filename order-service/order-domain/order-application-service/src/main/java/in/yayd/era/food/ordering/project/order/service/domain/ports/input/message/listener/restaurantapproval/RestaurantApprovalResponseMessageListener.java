package in.yayd.era.food.ordering.project.order.service.domain.ports.input.message.listener.restaurantapproval;

import in.yayd.era.food.ordering.project.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestaurantApprovalResponseMessageListener {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);

    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);

}