package in.yayd.era.food.ordering.project.customer.service.data.access.customer.repository;

import in.yayd.era.food.ordering.project.customer.service.data.access.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
}
