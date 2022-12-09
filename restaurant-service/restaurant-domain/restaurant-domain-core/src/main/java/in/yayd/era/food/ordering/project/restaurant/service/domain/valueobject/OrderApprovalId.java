package in.yayd.era.food.ordering.project.restaurant.service.domain.valueobject;

import in.yayd.era.food.ordering.project.domain.valueobject.BaseId;

import java.util.UUID;

public class OrderApprovalId extends BaseId<UUID> {
    public OrderApprovalId(UUID value) {
        super(value);
    }
}
