package in.yayd.era.food.ordering.project.payment.service.data.access.credithistory.adapter;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.payment.service.data.access.credithistory.entity.CreditHistoryEntity;
import in.yayd.era.food.ordering.project.payment.service.data.access.credithistory.mapper.CreditHistoryDataAccessMapper;
import in.yayd.era.food.ordering.project.payment.service.data.access.credithistory.repository.CreditHistoryJpaRepository;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditHistory;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.output.repository.CreditHistoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    public CreditHistoryRepositoryImpl(CreditHistoryJpaRepository creditHistoryJpaRepository,
                                       CreditHistoryDataAccessMapper creditHistoryDataAccessMapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.creditHistoryDataAccessMapper = creditHistoryDataAccessMapper;
    }

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return creditHistoryDataAccessMapper.creditHistoryEntityToCreditHistory(creditHistoryJpaRepository
                .save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory)));
    }

    @Override
    public Optional<List<CreditHistory>> findByCustomerId(CustomerId customerId) {
        Optional<List<CreditHistoryEntity>> creditHistory =
                creditHistoryJpaRepository.findByCustomerId(customerId.getValue());
        return creditHistory
                .map(creditHistoryList ->
                        creditHistoryList.stream()
                                .map(creditHistoryDataAccessMapper::creditHistoryEntityToCreditHistory)
                                .collect(Collectors.toList()));
    }
}
