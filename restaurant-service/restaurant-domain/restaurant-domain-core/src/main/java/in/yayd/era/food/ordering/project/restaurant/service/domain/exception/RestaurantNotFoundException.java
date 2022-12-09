package in.yayd.era.food.ordering.project.restaurant.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class RestaurantNotFoundException extends DomainException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
