package in.yayd.era.food.ordering.project.saga;

import in.yayd.era.food.ordering.project.domain.event.DomainEvent;

public interface SagaStep<T> {
    void process(T data);
    void rollback(T data);
}
