package in.yayd.era.food.ordering.project.restaurant.service.messaging.mapper;

import in.yayd.era.food.ordering.project.domain.valueobject.ProductId;
import in.yayd.era.food.ordering.project.domain.valueobject.RestaurantOrderStatus;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.OrderApprovalStatus;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import in.yayd.era.food.ordering.project.restaurant.service.domain.dto.RestaurantApprovalRequest;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Product;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderApprovedEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.event.OrderRejectedEvent;
import in.yayd.era.food.ordering.project.restaurant.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantMessagingDataMapper {
    public RestaurantApprovalRequest restaurantApprovalRequestAvroModelToRestaurantApproval(RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel) {
        return RestaurantApprovalRequest.builder()
                .id(restaurantApprovalRequestAvroModel.getId())
                .sagaId(restaurantApprovalRequestAvroModel.getSagaId())
                .restaurantId(restaurantApprovalRequestAvroModel.getRestaurantId())
                .orderId(restaurantApprovalRequestAvroModel.getOrderId())
                .restaurantOrderStatus(RestaurantOrderStatus.valueOf(restaurantApprovalRequestAvroModel
                        .getRestaurantOrderStatus().name()))
                .products(restaurantApprovalRequestAvroModel.getProducts()
                        .stream().map(avroModel ->
                                Product.Builder.builder()
                                        .productId(new ProductId(UUID.fromString(avroModel.getId())))
                                        .quantity(avroModel.getQuantity())
                                        .build())
                        .collect(Collectors.toList()))
                .price(restaurantApprovalRequestAvroModel.getPrice())
                .createdAt(restaurantApprovalRequestAvroModel.getCreatedAt())
                .build();
    }

    public RestaurantApprovalResponseAvroModel orderEventPayloadToRestaurantApprovalResponseAvroModel(
            String sagaId,
            OrderEventPayload orderEventPayload
    ) {
        return RestaurantApprovalResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setOrderId(orderEventPayload.getOrderId())
                .setRestaurantId(orderEventPayload.getRestaurantId())
                .setCreatedAt(orderEventPayload.getCreatedAt().toInstant())
                .setOrderApprovalStatus(OrderApprovalStatus.valueOf(orderEventPayload.getOrderApprovalStatus()))
                .setFailureMessages(orderEventPayload.getFailureMessages())
                .build();
    }
}
