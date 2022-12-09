package in.yayd.era.food.ordering.project.order.service.domain;

import in.yayd.era.food.ordering.project.order.service.domain.dto.message.PaymentResponse;
import in.yayd.era.food.ordering.project.order.service.domain.ports.input.service.message.listener.payment.PaymentResponseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}
