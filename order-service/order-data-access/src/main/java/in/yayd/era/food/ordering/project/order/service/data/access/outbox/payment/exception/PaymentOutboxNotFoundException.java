package in.yayd.era.food.ordering.project.order.service.data.access.outbox.payment.exception;

public class PaymentOutboxNotFoundException extends RuntimeException {
    public PaymentOutboxNotFoundException(String message) {
        super(message);
    }
}
