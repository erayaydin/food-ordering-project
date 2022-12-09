package in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);

}
