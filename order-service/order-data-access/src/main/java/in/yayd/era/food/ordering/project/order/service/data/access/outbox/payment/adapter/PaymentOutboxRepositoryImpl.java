package in.yayd.era.food.ordering.project.order.service.data.access.outbox.payment.adapter;

import in.yayd.era.food.ordering.project.order.service.data.access.outbox.payment.exception.PaymentOutboxNotFoundException;
import in.yayd.era.food.ordering.project.order.service.data.access.outbox.payment.mapper.PaymentOutboxDataAccessMapper;
import in.yayd.era.food.ordering.project.order.service.data.access.outbox.payment.repository.PaymentOutboxJpaRepository;
import in.yayd.era.food.ordering.project.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.saga.SagaStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PaymentOutboxRepositoryImpl implements PaymentOutboxRepository {
    private final PaymentOutboxJpaRepository paymentOutboxJpaRepository;
    private final PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper;

    public PaymentOutboxRepositoryImpl(PaymentOutboxJpaRepository paymentOutboxJpaRepository,
                                       PaymentOutboxDataAccessMapper paymentOutboxDataAccessMapper) {
        this.paymentOutboxJpaRepository = paymentOutboxJpaRepository;
        this.paymentOutboxDataAccessMapper = paymentOutboxDataAccessMapper;
    }

    @Override
    public OrderPaymentOutboxMessage save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        return paymentOutboxDataAccessMapper.paymentOutboxEntityToOrderPaymentOutboxMessage(
                paymentOutboxJpaRepository.save(
                        paymentOutboxDataAccessMapper.orderPaymentOutboxMessageToOutboxEntity(orderPaymentOutboxMessage)
                )
        );
    }

    @Override
    public Optional<List<OrderPaymentOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(
            String sagaType,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    ) {
        return Optional.of(
                paymentOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(sagaType, outboxStatus, Arrays.asList(sagaStatus))
                    .orElseThrow(() -> new PaymentOutboxNotFoundException("Payment outbox object could not be found for saga type " + sagaType))
                    .stream()
                    .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToOrderPaymentOutboxMessage)
                    .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<OrderPaymentOutboxMessage> findByTypeAndSagaIdAndSagaStatus(
            String type,
            UUID sagaId,
            SagaStatus... sagaStatus
    ) {
        return paymentOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId, Arrays.asList(sagaStatus))
                .map(paymentOutboxDataAccessMapper::paymentOutboxEntityToOrderPaymentOutboxMessage);
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatus(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        paymentOutboxJpaRepository.deleteByTypeAndOutboxStatusAndSagaStatusIn(
                type,
                outboxStatus,
                Arrays.asList(sagaStatus)
        );
    }
}
