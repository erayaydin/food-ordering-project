package in.yayd.era.food.ordering.project.customer.service;

import in.yayd.era.food.ordering.project.customer.service.domain.CustomerDomainService;
import in.yayd.era.food.ordering.project.customer.service.domain.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }
}
