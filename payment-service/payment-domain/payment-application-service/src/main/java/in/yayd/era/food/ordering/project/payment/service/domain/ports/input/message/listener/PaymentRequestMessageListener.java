package in.yayd.era.food.ordering.project.payment.service.domain.ports.input.message.listener;

import in.yayd.era.food.ordering.project.payment.service.domain.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);

}
