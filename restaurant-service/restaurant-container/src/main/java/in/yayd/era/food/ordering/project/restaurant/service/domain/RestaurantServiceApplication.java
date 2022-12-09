package in.yayd.era.food.ordering.project.restaurant.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "in.yayd.era.food.ordering.project.restaurant.service.data.access", "in.yayd.era.food.ordering.project.data.access" })
@EntityScan(basePackages = { "in.yayd.era.food.ordering.project.restaurant.service.data.access", "in.yayd.era.food.ordering.project.data.access" })
@SpringBootApplication(scanBasePackages = "in.yayd.era.food.ordering.project")
public class RestaurantServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
}
