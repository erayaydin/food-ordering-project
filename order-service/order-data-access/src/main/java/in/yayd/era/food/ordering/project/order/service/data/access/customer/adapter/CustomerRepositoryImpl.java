package in.yayd.era.food.ordering.project.order.service.data.access.customer.adapter;

import in.yayd.era.food.ordering.project.order.service.data.access.customer.mapper.CustomerDataAccessMapper;
import in.yayd.era.food.ordering.project.order.service.data.access.customer.repository.CustomerJpaRepository;
import in.yayd.era.food.ordering.project.order.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;
    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository, CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Optional<Customer> findCustomer(UUID customerId) {
        return customerJpaRepository.findById(customerId).map(customerDataAccessMapper::customerEntityToCustomer);
    }
}
