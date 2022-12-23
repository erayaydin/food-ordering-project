package in.yayd.era.food.ordering.project.order.service.messaging.publisher.kafka;

import in.yayd.era.food.ordering.project.kafka.order.avro.model.PaymentRequestAvroModel;
import in.yayd.era.food.ordering.project.kafka.producer.KafkaMessageHelper;
import in.yayd.era.food.ordering.project.kafka.producer.service.KafkaProducer;
import in.yayd.era.food.ordering.project.order.service.domain.config.OrderServiceConfigData;
import in.yayd.era.food.ordering.project.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import in.yayd.era.food.ordering.project.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import in.yayd.era.food.ordering.project.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import in.yayd.era.food.ordering.project.order.service.messaging.mapper.OrderMessagingDataMapper;
import in.yayd.era.food.ordering.project.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderPaymentEventKafkaPublisher implements PaymentRequestMessagePublisher {

    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public OrderPaymentEventKafkaPublisher(
            OrderMessagingDataMapper orderMessagingDataMapper,
            KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer,
            OrderServiceConfigData orderServiceConfigData,
            KafkaMessageHelper kafkaMessageHelper
    ) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(
            OrderPaymentOutboxMessage orderPaymentOutboxMessage,
            BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback
    ) {
        OrderPaymentEventPayload orderPaymentEventPayload =
                kafkaMessageHelper.getOrderEventPayload(orderPaymentOutboxMessage.getPayload(), OrderPaymentEventPayload.class);

        String sagaId = orderPaymentOutboxMessage.getSagaId().toString();

        log.info("Received OrderPaymentOutboxMessage for order id: {} and saga id: {}", orderPaymentEventPayload.getOrderId(), sagaId);

        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderPaymentEventToPaymentRequestAvroModel(sagaId, orderPaymentEventPayload);

            kafkaProducer.send(
                    orderServiceConfigData.getPaymentRequestTopicName(),
                    sagaId,
                    paymentRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(
                            orderServiceConfigData.getPaymentRequestTopicName(),
                            paymentRequestAvroModel, orderPaymentOutboxMessage,
                            outboxCallback,
                            orderPaymentEventPayload.getOrderId(),
                            "PaymentRequestAvroModel"
                    )
            );

            log.info("OrderPaymentEventPayload sent to Kafka for order id: {} and saga id: {}", orderPaymentEventPayload.getOrderId(), sagaId);
        } catch (Exception e) {
            log.error(
                    "Error while sending OrderPaymentEventPayload to kafka with order id: {} and saga id: {}, error: {}",
                    orderPaymentEventPayload.getOrderId(),
                    sagaId,
                    e.getMessage()
            );
        }
    }
}
