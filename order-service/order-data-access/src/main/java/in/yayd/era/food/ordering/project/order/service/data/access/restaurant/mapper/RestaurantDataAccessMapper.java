package in.yayd.era.food.ordering.project.order.service.data.access.restaurant.mapper;

import in.yayd.era.food.ordering.project.data.access.restaurant.entity.RestaurantEntity;
import in.yayd.era.food.ordering.project.data.access.restaurant.exception.RestaurantDataAccessException;
import in.yayd.era.food.ordering.project.domain.valueobject.Money;
import in.yayd.era.food.ordering.project.domain.valueobject.ProductId;
import in.yayd.era.food.ordering.project.domain.valueobject.RestaurantId;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Product;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {
    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity =
                restaurantEntities.stream().findFirst().orElseThrow(() ->
                        new RestaurantDataAccessException("Restaurant could not be found!"));

        List<Product> restaurantProducts = restaurantEntities.stream().map(entity ->
                new Product(new ProductId(entity.getProductId()), entity.getProductName(), new Money(entity.getProductPrice())))
                .toList();

        return Restaurant.Builder.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .isActive(restaurantEntity.getRestaurantIsActive())
                .build();
    }
}
