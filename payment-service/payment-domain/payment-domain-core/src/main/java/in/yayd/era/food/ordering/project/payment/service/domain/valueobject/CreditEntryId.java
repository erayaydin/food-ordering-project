package in.yayd.era.food.ordering.project.payment.service.domain.valueobject;

import in.yayd.era.food.ordering.project.domain.valueobject.BaseId;

import java.util.UUID;

public class CreditEntryId extends BaseId<UUID> {
    public CreditEntryId(UUID value) {
        super(value);
    }
}
