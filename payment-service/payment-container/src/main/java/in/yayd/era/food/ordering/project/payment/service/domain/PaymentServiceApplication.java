package in.yayd.era.food.ordering.project.payment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "in.yayd.era.food.ordering.project.payment.service.data.access")
@EntityScan(basePackages = "in.yayd.era.food.ordering.project.payment.service.data.access")
@SpringBootApplication(scanBasePackages = "in.yayd.era.food.ordering.project")
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
