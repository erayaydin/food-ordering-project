package in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.outbox.exception;

public class OrderOutboxNotFoundException extends RuntimeException {
    public OrderOutboxNotFoundException(String message) {
        super(message);
    }
}
