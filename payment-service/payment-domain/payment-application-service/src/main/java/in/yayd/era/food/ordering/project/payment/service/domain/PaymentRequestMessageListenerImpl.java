package in.yayd.era.food.ordering.project.payment.service.domain;

import in.yayd.era.food.ordering.project.payment.service.domain.dto.PaymentRequest;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {
    private final PaymentRequestHelper paymentRequestHelper;
    public PaymentRequestMessageListenerImpl(PaymentRequestHelper paymentRequestHelper) {
        this.paymentRequestHelper = paymentRequestHelper;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        paymentRequestHelper.persistPayment(paymentRequest);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        paymentRequestHelper.persistCancelPayment(paymentRequest);
    }
}
