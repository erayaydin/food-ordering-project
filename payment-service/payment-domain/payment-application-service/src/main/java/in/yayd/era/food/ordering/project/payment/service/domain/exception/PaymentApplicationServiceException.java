package in.yayd.era.food.ordering.project.payment.service.domain.exception;

import in.yayd.era.food.ordering.project.domain.exception.DomainException;

public class PaymentApplicationServiceException extends DomainException {
    public PaymentApplicationServiceException(String message) {
        super(message);
    }

    public PaymentApplicationServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
