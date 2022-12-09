package in.yayd.era.food.ordering.project.payment.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class PaymentNotFoundException extends DomainException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
