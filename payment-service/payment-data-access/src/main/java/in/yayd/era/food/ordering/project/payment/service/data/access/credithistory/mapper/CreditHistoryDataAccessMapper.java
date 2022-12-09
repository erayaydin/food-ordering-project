package in.yayd.era.food.ordering.project.payment.service.data.access.credithistory.mapper;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.domain.valueobject.Money;
import in.yayd.era.food.ordering.project.payment.service.data.access.credithistory.entity.CreditHistoryEntity;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.CreditHistory;
import in.yayd.era.food.ordering.project.payment.service.domain.valueobject.CreditHistoryId;
import org.springframework.stereotype.Component;

@Component
public class CreditHistoryDataAccessMapper {

    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity) {
        return CreditHistory.Builder.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                .customerId(new CustomerId(creditHistoryEntity.getCustomerId()))
                .amount(new Money(creditHistoryEntity.getAmount()))
                .transactionType(creditHistoryEntity.getType())
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .customerId(creditHistory.getCustomerId().getValue())
                .amount(creditHistory.getAmount().getAmount())
                .type(creditHistory.getTransactionType())
                .build();
    }

}
