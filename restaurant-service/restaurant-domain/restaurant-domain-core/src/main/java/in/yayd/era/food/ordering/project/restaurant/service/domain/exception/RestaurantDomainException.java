package in.yayd.era.food.ordering.project.restaurant.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class RestaurantDomainException extends DomainException {

    public RestaurantDomainException(String message) {
        super(message);
    }

    public RestaurantDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
