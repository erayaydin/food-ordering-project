package in.yayd.era.food.ordering.project.outbox;

public interface OutboxScheduler {
    void processOutboxMessage();
}
