package in.yayd.era.food.ordering.project.customer.service.domain.ports.input.service;

import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerCommand;
import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerResponse;

import javax.validation.Valid;

public interface CustomerApplicationService {
    CreateCustomerResponse createCustomer(@Valid CreateCustomerCommand createCustomerCommand);
}
