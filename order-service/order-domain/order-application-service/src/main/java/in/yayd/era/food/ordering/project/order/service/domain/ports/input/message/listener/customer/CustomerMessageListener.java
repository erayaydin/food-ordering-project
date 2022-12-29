package in.yayd.era.food.ordering.project.order.service.domain.ports.input.message.listener.customer;

import in.yayd.era.food.ordering.project.order.service.domain.dto.message.CustomerModel;

public interface CustomerMessageListener {
    void customerCreated(CustomerModel customerModel);
}
