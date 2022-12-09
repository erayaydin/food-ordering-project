package in.yayd.era.food.ordering.project.order.service.data.access.order.repository;

import in.yayd.era.food.ordering.project.order.service.data.access.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByTrackingId(UUID trackingId);
}
