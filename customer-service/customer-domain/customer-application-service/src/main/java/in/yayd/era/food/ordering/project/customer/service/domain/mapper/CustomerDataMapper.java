package in.yayd.era.food.ordering.project.customer.service.domain.mapper;

import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerCommand;
import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerResponse;
import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataMapper {
    public Customer createCustomerCommandToCustomer(CreateCustomerCommand createCustomerCommand) {
        return new Customer(new CustomerId(createCustomerCommand.getCustomerId()),
                createCustomerCommand.getUsername(),
                createCustomerCommand.getFirstName(),
                createCustomerCommand.getLastName());
    }

    public CreateCustomerResponse customerToCreateCustomerResponse(Customer customer, String message) {
        return new CreateCustomerResponse(customer.getId().getValue(), message);
    }
}
