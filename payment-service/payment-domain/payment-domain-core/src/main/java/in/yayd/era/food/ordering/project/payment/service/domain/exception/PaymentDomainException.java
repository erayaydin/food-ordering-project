package in.yayd.era.food.ordering.project.payment.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class PaymentDomainException extends DomainException {
    public PaymentDomainException(String message) {
        super(message);
    }

    public PaymentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
