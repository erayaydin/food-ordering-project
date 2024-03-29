package in.yayd.era.food.ordering.project.restaurant.service.domain.mapper;

import in.yayd.era.food.ordering.project.domain.valueobject.Money;
import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.domain.valueobject.OrderStatus;
import in.yayd.era.food.ordering.project.domain.valueobject.RestaurantId;
import in.yayd.era.food.ordering.project.restaurant.service.domain.dto.RestaurantApprovalRequest;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.OrderDetail;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Product;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Restaurant;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovalEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataMapper {
    public Restaurant restaurantApprovalRequestToRestaurant(RestaurantApprovalRequest restaurantApprovalRequest) {
        return Restaurant.Builder.builder()
                .restaurantId(new RestaurantId(UUID.fromString(restaurantApprovalRequest.getRestaurantId())))
                .orderDetail(
                        OrderDetail.Builder.builder()
                                .orderId(new OrderId(UUID.fromString(restaurantApprovalRequest.getOrderId())))
                                .products(
                                        restaurantApprovalRequest.getProducts().stream().map(
                                                product -> Product.Builder.builder()
                                                        .productId(product.getId())
                                                        .quantity(product.getQuantity())
                                                        .build()
                                        ).collect(Collectors.toList()))
                                .totalAmount(new Money(restaurantApprovalRequest.getPrice()))
                                .orderStatus(OrderStatus.valueOf(restaurantApprovalRequest.getRestaurantOrderStatus().name()))
                                .build()
                )
                .build();
    }

    public OrderEventPayload orderApprovalEventToOrderEventPayload(OrderApprovalEvent orderApprovalEvent) {
        return OrderEventPayload.builder()
                .orderId(orderApprovalEvent.getOrderApproval().getOrderId().getValue().toString())
                .restaurantId(orderApprovalEvent.getRestaurantId().getValue().toString())
                .orderApprovalStatus(orderApprovalEvent.getOrderApproval().getOrderApprovalStatus().name())
                .createdAt(orderApprovalEvent.getCreatedAt())
                .failureMessages(orderApprovalEvent.getFailureMessages())
                .build();
    }
}
