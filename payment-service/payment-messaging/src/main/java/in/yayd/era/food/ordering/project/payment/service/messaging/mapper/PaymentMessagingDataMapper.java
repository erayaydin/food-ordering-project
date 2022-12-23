package in.yayd.era.food.ordering.project.payment.service.messaging.mapper;

import in.yayd.era.food.ordering.project.domain.valueobject.PaymentOrderStatus;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.PaymentRequestAvroModel;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.PaymentResponseAvroModel;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.PaymentStatus;
import in.yayd.era.food.ordering.project.payment.service.domain.dto.PaymentRequest;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentCancelledEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentCompletedEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.event.PaymentFailedEvent;
import in.yayd.era.food.ordering.project.payment.service.domain.outbox.model.OrderEventPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMessagingDataMapper {

    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId())
                .sagaId(paymentRequestAvroModel.getSagaId())
                .customerId(paymentRequestAvroModel.getCustomerId())
                .orderId(paymentRequestAvroModel.getOrderId())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.valueOf(paymentRequestAvroModel.getPaymentOrderStatus().name()))
                .build();
    }

    public PaymentResponseAvroModel orderEventPayloadToPaymentResponseAvroModel(
            String sagaId,
            OrderEventPayload orderEventPayload
    ) {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSagaId(sagaId)
                .setPaymentId(orderEventPayload.getPaymentId())
                .setCustomerId(orderEventPayload.getCustomerId())
                .setOrderId(orderEventPayload.getOrderId())
                .setPrice(orderEventPayload.getPrice())
                .setCreatedAt(orderEventPayload.getCreatedAt().toInstant()) // ??
                .setPaymentStatus(PaymentStatus.valueOf(orderEventPayload.getPaymentStatus()))
                .setFailureMessages(orderEventPayload.getFailureMessages())
                .build();
    }

}
