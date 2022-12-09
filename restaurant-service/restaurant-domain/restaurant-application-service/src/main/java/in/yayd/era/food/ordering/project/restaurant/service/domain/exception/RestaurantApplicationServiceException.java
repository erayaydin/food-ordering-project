package in.yayd.era.food.ordering.project.restaurant.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class RestaurantApplicationServiceException extends DomainException {
    public RestaurantApplicationServiceException(String message) {
        super(message);
    }

    public RestaurantApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
