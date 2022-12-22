package in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.repository;

import in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.entity.ApprovalOutboxEntity;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.saga.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApprovalOutboxJpaRepository extends JpaRepository<ApprovalOutboxEntity, UUID> {
    Optional<List<ApprovalOutboxEntity>> findByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            List<SagaStatus> sagaStatus
    );

    Optional<ApprovalOutboxEntity> findByTypeAndSagaIdAndSagaStatusIn(
            String type,
            UUID sagaId,
            List<SagaStatus> sagaStatus
    );

    void deleteByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            List<SagaStatus> sagaStatus
    );
}
