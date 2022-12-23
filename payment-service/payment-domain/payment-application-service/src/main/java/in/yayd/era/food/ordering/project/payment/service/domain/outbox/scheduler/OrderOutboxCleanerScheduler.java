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
public class OrderOutboxCleanerScheduler implements OutboxScheduler {

    private final OrderOutboxHelper orderOutboxHelper;

    public OrderOutboxCleanerScheduler(OrderOutboxHelper orderOutboxHelper) {
        this.orderOutboxHelper = orderOutboxHelper;
    }


    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void processOutboxMessage() {
        Optional<List<OrderOutboxMessage>> maybeOutboxMessages =
                orderOutboxHelper.getOrderOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);

        if (maybeOutboxMessages.isEmpty() || maybeOutboxMessages.get().size() == 0) {
            return;
        }

        List<OrderOutboxMessage> outboxMessages = maybeOutboxMessages.get();
        log.info(
                "Received {} OrderOutboxMessage for clean-up!",
                outboxMessages.size()
        );

        orderOutboxHelper.deleteOrderOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
        log.info("Deleted {} OrderOutboxMessage!", outboxMessages.size());
    }
}
