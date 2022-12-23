package in.yayd.era.food.ordering.project.payment.service.domain.outbox.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.yayd.era.food.ordering.project.domain.DomainConstants;
import in.yayd.era.food.ordering.project.domain.valueobject.PaymentStatus;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.payment.service.domain.exception.PaymentDomainException;
import in.yayd.era.food.ordering.project.payment.service.domain.outbox.model.OrderEventPayload;
import in.yayd.era.food.ordering.project.payment.service.domain.outbox.model.OrderOutboxMessage;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository.OrderOutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static in.yayd.era.food.ordering.project.domain.DomainConstants.DefaultTimezone;
import static in.yayd.era.food.ordering.project.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
public class OrderOutboxHelper {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;

    public OrderOutboxHelper(OrderOutboxRepository orderOutboxRepository, ObjectMapper objectMapper) {
        this.orderOutboxRepository = orderOutboxRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional(readOnly = true)
    public Optional<OrderOutboxMessage> getCompletedOrderOutboxMessageBySagaIdAndPaymentStatus(
            UUID sagaId,
            PaymentStatus paymentStatus
    ) {
        return orderOutboxRepository.findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(
                ORDER_SAGA_NAME,
                sagaId,
                paymentStatus,
                OutboxStatus.COMPLETED
        );
    }

    @Transactional(readOnly = true)
    public Optional<List<OrderOutboxMessage>> getOrderOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        return orderOutboxRepository.findByTypeAndOutboxStatus(ORDER_SAGA_NAME, outboxStatus);
    }

    @Transactional
    public void deleteOrderOutboxMessageByOutboxStatus(OutboxStatus outboxStatus) {
        orderOutboxRepository.deleteByTypeAndOutboxStatus(ORDER_SAGA_NAME, outboxStatus);
    }
    
    @Transactional
    public void saveOrderOutboxMessage(
            OrderEventPayload orderEventPayload,
            PaymentStatus paymentStatus,
            OutboxStatus outboxStatus,
            UUID sagaId
    ) {
        save(
                OrderOutboxMessage.builder()
                        .id(UUID.randomUUID())
                        .sagaId(sagaId)
                        .createdAt(orderEventPayload.getCreatedAt())
                        .processedAt(ZonedDateTime.now(ZoneId.of(DefaultTimezone)))
                        .type(ORDER_SAGA_NAME)
                        .payload(createPayload(orderEventPayload))
                        .paymentStatus(paymentStatus)
                        .outboxStatus(outboxStatus)
                        .build()
        );
    }

    @Transactional
    public void updateOutboxMessage(OrderOutboxMessage orderOutboxMessage, OutboxStatus outboxStatus) {
        orderOutboxMessage.setOutboxStatus(outboxStatus);
        save(orderOutboxMessage);
        log.info("Order outbox table status is updated as : {}", outboxStatus.name());
    }

    private String createPayload(OrderEventPayload orderEventPayload) {
        try {
            return objectMapper.writeValueAsString(orderEventPayload);
        } catch (JsonProcessingException e) {
            log.error("Could not create OrderEventPayload json!", e);
            throw new PaymentDomainException("Could not create OrderEventPayload json!");
        }
    }

    private void save(OrderOutboxMessage orderOutboxMessage) {
        OrderOutboxMessage response = orderOutboxRepository.save(orderOutboxMessage);

        if (response == null) {
            log.error("Could not save OrderOutboxMessage!");
            throw new PaymentDomainException("Could not save OrderOutboxMessage!");
        }

        log.info("OrderOutboxMessage is saved with id: {}", orderOutboxMessage.getId());
    }
}
