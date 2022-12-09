package in.yayd.era.food.ordering.project.order.service.domain;

import in.yayd.era.food.ordering.project.order.service.domain.dto.message.RestaurantApprovalResponse;
import in.yayd.era.food.ordering.project.order.service.domain.ports.input.service.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {
    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

    }
}
