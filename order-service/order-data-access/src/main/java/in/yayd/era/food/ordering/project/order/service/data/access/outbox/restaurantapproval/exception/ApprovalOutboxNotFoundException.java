package in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.exception;

public class ApprovalOutboxNotFoundException extends RuntimeException {
    public ApprovalOutboxNotFoundException(String message) {
        super(message);
    }
}
