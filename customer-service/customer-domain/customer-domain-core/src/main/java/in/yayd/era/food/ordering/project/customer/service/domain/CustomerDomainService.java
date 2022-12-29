package in.yayd.era.food.ordering.project.customer.service.domain;

import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.customer.service.domain.event.CustomerCreatedEvent;

public interface CustomerDomainService {
    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);
}
