package in.yayd.era.food.ordering.project.customer.service.domain.ports.output.repository;

import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;

public interface CustomerRepository {
    Customer createCustomer(Customer customer);
}
