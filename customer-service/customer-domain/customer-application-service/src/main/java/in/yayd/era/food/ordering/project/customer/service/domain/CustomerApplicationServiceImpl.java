package in.yayd.era.food.ordering.project.customer.service.domain;

import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerCommand;
import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerResponse;
import in.yayd.era.food.ordering.project.customer.service.domain.event.CustomerCreatedEvent;
import in.yayd.era.food.ordering.project.customer.service.domain.mapper.CustomerDataMapper;
import in.yayd.era.food.ordering.project.customer.service.domain.ports.input.service.CustomerApplicationService;
import in.yayd.era.food.ordering.project.customer.service.domain.ports.output.message.publisher.CustomerMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class CustomerApplicationServiceImpl implements CustomerApplicationService {

    private final CustomerCreateCommandHandler customerCreateCommandHandler;

    private final CustomerDataMapper customerDataMapper;

    private final CustomerMessagePublisher customerMessagePublisher;

    public CustomerApplicationServiceImpl(CustomerCreateCommandHandler customerCreateCommandHandler, CustomerDataMapper customerDataMapper, CustomerMessagePublisher customerMessagePublisher) {
        this.customerCreateCommandHandler = customerCreateCommandHandler;
        this.customerDataMapper = customerDataMapper;
        this.customerMessagePublisher = customerMessagePublisher;
    }


    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerCommand createCustomerCommand) {
        CustomerCreatedEvent customerCreatedEvent = customerCreateCommandHandler.createCustomer(createCustomerCommand);
        customerMessagePublisher.publish(customerCreatedEvent);
        return customerDataMapper
                .customerToCreateCustomerResponse(customerCreatedEvent.getCustomer(),
                        "Customer saved successfully!");
    }
}
