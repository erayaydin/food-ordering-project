package in.yayd.era.food.ordering.project.payment.service.domain.outbox.scheduler;

import in.yayd.era.food.ordering.project.outbox.OutboxScheduler;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import in.yayd.era.food.ordering.project.payment.service.domain.outbox.model.OrderOutboxMessage;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.message.publisher.PaymentResponseMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderOutboxScheduler implements OutboxScheduler {

    private final OrderOutboxHelper orderOutboxHelper;
    private final PaymentResponseMessagePublisher paymentResponseMessagePublisher;

    public OrderOutboxScheduler(OrderOutboxHelper orderOutboxHelper, PaymentResponseMessagePublisher paymentResponseMessagePublisher) {
        this.orderOutboxHelper = orderOutboxHelper;
        this.paymentResponseMessagePublisher = paymentResponseMessagePublisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${payment-service.outbox-scheduler-fixed-rate}", initialDelayString = "${payment-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<OrderOutboxMessage>> maybeOutboxMessages =
                orderOutboxHelper.getOrderOutboxMessageByOutboxStatus(OutboxStatus.STARTED);

        if (maybeOutboxMessages.isEmpty() || maybeOutboxMessages.get().size() == 0) {
            return;
        }

        List<OrderOutboxMessage> outboxMessages = maybeOutboxMessages.get();
        log.info(
                "Received {} OrderOutboxMessage with ids {}, sending to kafka!",
                outboxMessages.size(),
                outboxMessages.stream().map(
                        outboxMessage -> outboxMessage.getId().toString()
                ).collect(Collectors.joining(","))
        );
        outboxMessages.forEach(orderOutboxMessage ->
                paymentResponseMessagePublisher.publish(orderOutboxMessage, orderOutboxHelper::updateOutboxMessage)
        );
        log.info("{} OrderOutboxMessage sent to message bus!", outboxMessages.size());
    }
}
