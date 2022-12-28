package in.yayd.era.food.ordering.project.restaurant.service.domain.outbox.model;

import in.yayd.era.food.ordering.project.domain.valueobject.OrderApprovalStatus;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderOutboxMessage {
    private UUID id;
    private UUID sagaId;
    private ZonedDateTime createdAt;
    private ZonedDateTime processedAt;
    private String type;
    private String payload;
    private OutboxStatus outboxStatus;
    private OrderApprovalStatus approvalStatus;
    private int version;

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}
