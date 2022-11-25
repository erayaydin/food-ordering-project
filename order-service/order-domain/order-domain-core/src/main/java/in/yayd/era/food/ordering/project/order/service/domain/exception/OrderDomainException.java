package in.yayd.era.food.ordering.project.order.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
