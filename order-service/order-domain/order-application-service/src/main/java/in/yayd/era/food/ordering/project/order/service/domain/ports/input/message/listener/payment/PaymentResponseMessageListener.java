package in.yayd.era.food.ordering.project.order.service.domain.ports.input.message.listener.payment;

import in.yayd.era.food.ordering.project.order.service.domain.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {

    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);

}
