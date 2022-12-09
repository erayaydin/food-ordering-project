package in.yayd.era.food.ordering.project.restaurant.service.domain.ports.output.repository;

import in.yayd.era.food.ordering.project.restaurant.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {
    OrderApproval save(OrderApproval orderApproval);
}
