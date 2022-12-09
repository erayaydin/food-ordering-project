package in.yayd.era.food.ordering.project.payment.service.data.access.creditentry.adapter;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.payment.service.data.access.creditentry.mapper.CreditEntryDataAccessMapper;
import in.yayd.era.food.ordering.project.payment.service.data.access.creditentry.repository.CreditEntryJpaRepository;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditEntry;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository.CreditEntryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditEntryRepositoryImpl implements CreditEntryRepository {

    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    public CreditEntryRepositoryImpl(CreditEntryJpaRepository creditEntryJpaRepository,
                                     CreditEntryDataAccessMapper creditEntryDataAccessMapper) {
        this.creditEntryJpaRepository = creditEntryJpaRepository;
        this.creditEntryDataAccessMapper = creditEntryDataAccessMapper;
    }

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper
                .creditEntryEntityToCreditEntry(creditEntryJpaRepository
                        .save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry)));
    }

    @Override
    public Optional<CreditEntry> findByCustomerId(CustomerId customerId) {
        return creditEntryJpaRepository
                .findByCustomerId(customerId.getValue())
                .map(creditEntryDataAccessMapper::creditEntryEntityToCreditEntry);
    }
}
