package in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.mapper;

import in.yayd.era.food.ordering.project.data.access.restaurant.entity.RestaurantEntity;
import in.yayd.era.food.ordering.project.data.access.restaurant.exception.RestaurantDataAccessException;
import in.yayd.era.food.ordering.project.domain.valueobject.Money;
import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.domain.valueobject.ProductId;
import in.yayd.era.food.ordering.project.domain.valueobject.RestaurantId;
import in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.entity.OrderApprovalEntity;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.OrderApproval;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.OrderDetail;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Product;
import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.Restaurant;
import in.yayd.era.food.ordering.project.restaurant.service.domain.valueobject.OrderApprovalId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getOrderDetail().getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("No restaurants found!"));

        List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
                        Product.Builder.builder()
                                .productId(new ProductId(entity.getProductId()))
                                .name(entity.getProductName())
                                .price(new Money(entity.getProductPrice()))
                                .available(entity.getProductAvailable())
                                .build())
                .collect(Collectors.toList());

        return Restaurant.Builder.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .orderDetail(OrderDetail.Builder.builder()
                        .products(restaurantProducts)
                        .build())
                .active(restaurantEntity.getRestaurantIsActive())
                .build();
    }

    public OrderApprovalEntity orderApprovalToOrderApprovalEntity(OrderApproval orderApproval) {
        return OrderApprovalEntity.builder()
                .id(orderApproval.getId().getValue())
                .restaurantId(orderApproval.getRestaurantId().getValue())
                .orderId(orderApproval.getOrderId().getValue())
                .status(orderApproval.getOrderApprovalStatus())
                .build();
    }

    public OrderApproval orderApprovalEntityToOrderApproval(OrderApprovalEntity orderApprovalEntity) {
        return OrderApproval.Builder.builder()
                .orderApprovalId(new OrderApprovalId(orderApprovalEntity.getId()))
                .restaurantId(new RestaurantId(orderApprovalEntity.getRestaurantId()))
                .orderId(new OrderId(orderApprovalEntity.getOrderId()))
                .orderApprovalStatus(orderApprovalEntity.getStatus())
                .build();
    }

}
