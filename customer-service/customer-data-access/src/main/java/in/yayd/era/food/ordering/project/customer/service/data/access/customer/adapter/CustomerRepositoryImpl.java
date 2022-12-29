package in.yayd.era.food.ordering.project.customer.service.data.access.customer.adapter;

import in.yayd.era.food.ordering.project.customer.service.data.access.customer.mapper.CustomerDataAccessMapper;
import in.yayd.era.food.ordering.project.customer.service.data.access.customer.repository.CustomerJpaRepository;
import in.yayd.era.food.ordering.project.customer.service.domain.entity.Customer;
import in.yayd.era.food.ordering.project.customer.service.domain.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerDataAccessMapper customerDataAccessMapper;

    public CustomerRepositoryImpl(CustomerJpaRepository customerJpaRepository, CustomerDataAccessMapper customerDataAccessMapper) {
        this.customerJpaRepository = customerJpaRepository;
        this.customerDataAccessMapper = customerDataAccessMapper;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJpaRepository.save(customerDataAccessMapper.customerToCustomerEntity(customer)));
    }
}
