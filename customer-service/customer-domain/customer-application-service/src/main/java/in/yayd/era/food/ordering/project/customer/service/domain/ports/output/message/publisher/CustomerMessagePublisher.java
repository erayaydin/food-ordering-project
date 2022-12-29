package in.yayd.era.food.ordering.project.customer.service.domain.ports.output.message.publisher;

import in.yayd.era.food.ordering.project.customer.service.domain.event.CustomerCreatedEvent;

public interface CustomerMessagePublisher {
    void publish(CustomerCreatedEvent customerCreatedEvent);
}
