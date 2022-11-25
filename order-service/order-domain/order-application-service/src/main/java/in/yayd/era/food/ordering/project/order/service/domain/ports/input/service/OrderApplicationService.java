package in.yayd.era.food.ordering.project.order.service.domain.ports.input.service;

import in.yayd.era.food.ordering.project.order.service.domain.dto.create.CreateOrderCommand;
import in.yayd.era.food.ordering.project.order.service.domain.dto.create.CreateOrderResponse;
import in.yayd.era.food.ordering.project.order.service.domain.dto.track.TrackOrderQuery;
import in.yayd.era.food.ordering.project.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);

}
