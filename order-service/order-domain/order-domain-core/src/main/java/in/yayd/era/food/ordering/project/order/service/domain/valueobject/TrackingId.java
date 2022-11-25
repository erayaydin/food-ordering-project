package in.yayd.era.food.ordering.project.order.service.domain.valueobject;

import in.yayd.era.food.ordering.project.domain.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
