package in.yayd.era.food.ordering.project.customer.service.data.access.customer.mapper;

import in.yayd.era.food.ordering.project.customer.service.data.access.customer.entity.CustomerEntity;
import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataAccessMapper {
    public Customer customerEntityToCustomer(CustomerEntity customerEntity) {
        return new Customer(new CustomerId(customerEntity.getId()),
                customerEntity.getUsername(),
                customerEntity.getFirstName(),
                customerEntity.getLastName());
    }

    public CustomerEntity customerToCustomerEntity(Customer customer) {
        return CustomerEntity.builder()
                .id(customer.getId().getValue())
                .username(customer.getUsername())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }
}
