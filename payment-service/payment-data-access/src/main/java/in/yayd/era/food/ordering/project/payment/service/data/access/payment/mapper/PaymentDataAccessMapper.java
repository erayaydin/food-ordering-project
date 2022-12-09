package in.yayd.era.food.ordering.project.payment.service.data.access.payment.mapper;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.domain.valueobject.Money;
import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.payment.service.data.access.payment.entity.PaymentEntity;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.Payment;
import in.yayd.era.food.ordering.project.payment.service.domain.valueobject.PaymentId;
import org.springframework.stereotype.Component;

@Component
public class PaymentDataAccessMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId().getValue())
                .customerId(payment.getCustomerId().getValue())
                .orderId(payment.getOrderId().getValue())
                .price(payment.getPrice().getAmount())
                .status(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.Builder.builder()
                .paymentId(new PaymentId(paymentEntity.getId()))
                .customerId(new CustomerId(paymentEntity.getCustomerId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .price(new Money(paymentEntity.getPrice()))
                .createdAt(paymentEntity.getCreatedAt())
                .build();
    }

}
