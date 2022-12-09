package in.yayd.era.food.ordering.project.payment.service.domain.mapper;

import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;
import in.yayd.era.food.ordering.project.domain.valueobject.Money;
import in.yayd.era.food.ordering.project.domain.valueobject.OrderId;
import in.yayd.era.food.ordering.project.payment.service.domain.dto.PaymentRequest;
import in.yayd.era.food.ordering.project.payment.service.domain.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {
    public Payment paymentRequestModelToPayment(PaymentRequest paymentRequest) {
        return Payment.Builder
                .builder()
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .customerId(new CustomerId(UUID.fromString(paymentRequest.getCustomerId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
