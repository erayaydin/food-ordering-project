package in.yayd.era.food.ordering.project.payment.service.messaging.publisher.kafka;

import in.yayd.era.food.ordering.project.domain.event.publisher.DomainEventPublisher;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.PaymentResponseAvroModel;
import in.yayd.era.food.ordering.project.kafka.producer.KafkaMessageHelper;
import in.yayd.era.food.ordering.project.kafka.producer.service.KafkaProducer;
import in.yayd.era.food.ordering.project.payment.service.domain.config.PaymentServiceConfigData;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentFailedEvent;
import in.yayd.era.food.ordering.project.payment.service.messaging.mapper.PaymentMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentFailedKafkaMessagePublisher implements DomainEventPublisher<PaymentFailedEvent> {

    private final PaymentMessagingDataMapper paymentMessagingDataMapper;
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final PaymentServiceConfigData paymentServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public PaymentFailedKafkaMessagePublisher(
            PaymentMessagingDataMapper paymentMessagingDataMapper,
            KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer,
            PaymentServiceConfigData paymentServiceConfigData,
            KafkaMessageHelper kafkaMessageHelper) {
        this.paymentMessagingDataMapper = paymentMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.paymentServiceConfigData = paymentServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(PaymentFailedEvent domainEvent) {
        String orderId = domainEvent.getPayment().getOrderId().getValue().toString();

        log.info("Received PaymentFailedEvent for order id: {}", orderId);

        try {
            PaymentResponseAvroModel paymentResponseAvroModel =
                    paymentMessagingDataMapper.paymentFailedEventToPaymentResponseAvroModel(domainEvent);

            kafkaProducer.send(
                    paymentServiceConfigData.getPaymentResponseTopicName(),
                    orderId,
                    paymentResponseAvroModel,
                    kafkaMessageHelper.getKafkaCallback(
                            paymentServiceConfigData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            orderId,
                            "PaymentResponseAvroModel"));
            log.info("PaymentResponseAvroModel sent to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending PaymentResponseAvroModel message to kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
