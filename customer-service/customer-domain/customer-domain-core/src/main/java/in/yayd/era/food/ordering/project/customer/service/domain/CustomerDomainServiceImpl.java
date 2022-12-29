package in.yayd.era.food.ordering.project.customer.service.domain;

import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.customer.service.domain.event.CustomerCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class CustomerDomainServiceImpl implements CustomerDomainService {
    @Override
    public CustomerCreatedEvent validateAndInitiateCustomer(Customer customer) {
        //Any Business logic required to run for a customer creation
        log.info("Customer with id: {} is initiated", customer.getId().getValue());
        return new CustomerCreatedEvent(customer, ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
