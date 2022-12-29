package in.yayd.era.food.ordering.project.customer.service.domain;

import in.yayd.era.food.ordering.project.customer.service.domain.create.CreateCustomerCommand;
import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.customer.service.domain.event.CustomerCreatedEvent;
import in.yayd.era.food.ordering.project.customer.service.domain.exception.CustomerDomainException;
import in.yayd.era.food.ordering.project.customer.service.domain.mapper.CustomerDataMapper;
import in.yayd.era.food.ordering.project.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CustomerCreateCommandHandler {
    private final CustomerDomainService customerDomainService;

    private final CustomerRepository customerRepository;

    private final CustomerDataMapper customerDataMapper;

    public CustomerCreateCommandHandler(CustomerDomainService customerDomainService, CustomerRepository customerRepository, CustomerDataMapper customerDataMapper) {
        this.customerDomainService = customerDomainService;
        this.customerRepository = customerRepository;
        this.customerDataMapper = customerDataMapper;
    }

    @Transactional
    public CustomerCreatedEvent createCustomer(CreateCustomerCommand createCustomerCommand) {
        Customer customer = customerDataMapper.createCustomerCommandToCustomer(createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);
        Customer savedCustomer = customerRepository.createCustomer(customer);
        if (savedCustomer == null) {
            log.error("Could not save customer with id: {}", createCustomerCommand.getCustomerId());
            throw new CustomerDomainException("Could not save customer with id " +
                    createCustomerCommand.getCustomerId());
        }
        log.info("Returning CustomerCreatedEvent for customer id: {}", createCustomerCommand.getCustomerId());
        return customerCreatedEvent;
    }
}
