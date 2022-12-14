package in.yayd.era.food.ordering.project.saga;

import in.yayd.era.food.ordering.project.domain.event.DomainEvent;

public interface SagaStep<T, S extends DomainEvent, U extends DomainEvent> {
    S process(T data);
    U rollback(T data);
}
