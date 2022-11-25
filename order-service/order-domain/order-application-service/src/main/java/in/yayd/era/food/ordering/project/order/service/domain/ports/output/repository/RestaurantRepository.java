package in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository;

import in.yayd.era.food.ordering.project.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {

    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);

}
