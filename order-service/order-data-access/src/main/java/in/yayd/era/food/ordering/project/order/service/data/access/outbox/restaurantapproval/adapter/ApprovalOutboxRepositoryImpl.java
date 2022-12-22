package in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.adapter;

import in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.exception.ApprovalOutboxNotFoundException;
import in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.mapper.ApprovalOutboxDataAccessMapper;
import in.yayd.era.food.ordering.project.order.service.data.access.outbox.restaurantapproval.repository.ApprovalOutboxJpaRepository;
import in.yayd.era.food.ordering.project.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.ApprovalOutboxRepository;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.saga.SagaStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ApprovalOutboxRepositoryImpl implements ApprovalOutboxRepository {
    private final ApprovalOutboxJpaRepository approvalOutboxJpaRepository;
    private final ApprovalOutboxDataAccessMapper approvalOutboxDataAccessMapper;

    public ApprovalOutboxRepositoryImpl(ApprovalOutboxJpaRepository approvalOutboxJpaRepository,
                                        ApprovalOutboxDataAccessMapper approvalOutboxDataAccessMapper) {
        this.approvalOutboxJpaRepository = approvalOutboxJpaRepository;
        this.approvalOutboxDataAccessMapper = approvalOutboxDataAccessMapper;
    }

    @Override
    public OrderApprovalOutboxMessage save(OrderApprovalOutboxMessage orderApprovalOutboxMessage) {
        return approvalOutboxDataAccessMapper.approvalOutboxEntityToOrderApprovalOutboxMessage(
                approvalOutboxJpaRepository.save(
                        approvalOutboxDataAccessMapper.orderCreatedOutboxMessageToOutboxEntity(orderApprovalOutboxMessage)
                )
        );
    }

    @Override
    public Optional<List<OrderApprovalOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(
            String sagaType,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    ) {
        return Optional.of(approvalOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(sagaType, outboxStatus,
                        Arrays.asList(sagaStatus))
                .orElseThrow(() -> new ApprovalOutboxNotFoundException("Approval outbox object " +
                        "could be found for saga type " + sagaType))
                .stream()
                .map(approvalOutboxDataAccessMapper::approvalOutboxEntityToOrderApprovalOutboxMessage)
                .collect(Collectors.toList()));
    }

    @Override
    public Optional<OrderApprovalOutboxMessage> findByTypeAndSagaIdAndSagaStatus(
            String type,
            UUID sagaId,
            SagaStatus... sagaStatus
    ) {
        return approvalOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId,
                        Arrays.asList(sagaStatus))
                .map(approvalOutboxDataAccessMapper::approvalOutboxEntityToOrderApprovalOutboxMessage);

    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        approvalOutboxJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus, Arrays.asList(sagaStatus));
    }
}
