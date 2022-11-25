package in.yayd.era.food.ordering.project.domain.event.publisher;

import in.yayd.era.food.ordering.project.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
    void publish(T domainEvent);
}
