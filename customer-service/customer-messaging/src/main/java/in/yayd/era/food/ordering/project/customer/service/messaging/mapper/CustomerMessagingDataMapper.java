package in.yayd.era.food.ordering.project.customer.service.messaging.mapper;

import in.yayd.era.food.ordering.project.customer.service.domain.event.CustomerCreatedEvent;
import in.yayd.era.food.ordering.project.kafka.order.avro.model.CustomerAvroModel;
import org.springframework.stereotype.Component;

@Component
public class CustomerMessagingDataMapper {
    public CustomerAvroModel paymentResponseAvroModelToPaymentResponse(CustomerCreatedEvent customerCreatedEvent) {
        return CustomerAvroModel.newBuilder()
                .setId(customerCreatedEvent.getCustomer().getId().getValue().toString())
                .setUsername(customerCreatedEvent.getCustomer().getUsername())
                .setFirstName(customerCreatedEvent.getCustomer().getFirstName())
                .setLastName(customerCreatedEvent.getCustomer().getLastName())
                .build();
    }
}
