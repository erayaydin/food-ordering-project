package in.yayd.era.food.ordering.project.payment.service.data.access.outbox.exception;

public class OrderOutboxNotFoundException extends RuntimeException {
    public OrderOutboxNotFoundException(String message) {
        super(message);
    }
}
