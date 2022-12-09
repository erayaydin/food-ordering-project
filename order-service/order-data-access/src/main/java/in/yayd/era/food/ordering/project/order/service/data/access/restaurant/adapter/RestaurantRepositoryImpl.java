package in.yayd.era.food.ordering.project.order.service.data.access.restaurant.adapter;

import in.yayd.era.food.ordering.project.order.service.data.access.restaurant.entity.RestaurantEntity;
import in.yayd.era.food.ordering.project.order.service.data.access.restaurant.mapper.RestaurantDataAccessMapper;
import in.yayd.era.food.ordering.project.order.service.data.access.restaurant.repository.RestaurantJpaRepository;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Restaurant;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantDataAccessMapper restaurantDataAccessMapper;

    public RestaurantRepositoryImpl(RestaurantJpaRepository restaurantJpaRepository, RestaurantDataAccessMapper restaurantDataAccessMapper) {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.restaurantDataAccessMapper = restaurantDataAccessMapper;
    }

    @Override
    public Optional<Restaurant> findRestaurantInformation(Restaurant restaurant) {
        List<UUID> restaurantProducts =
                restaurantDataAccessMapper.restaurantToRestaurantProducts(restaurant);

        Optional<List<RestaurantEntity>> restaurantEntities = restaurantJpaRepository
                .findByRestaurantIdAndProductIdIn(restaurant.getId().getValue(), restaurantProducts);

        return restaurantEntities.map(restaurantDataAccessMapper::restaurantEntityToRestaurant);
    }
}
