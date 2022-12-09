package in.yayd.era.food.ordering.project.payment.service.domain.valueobject;

import in.yayd.era.food.ordering.project.domain.valueobject.BaseId;

import java.util.UUID;
public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
