package in.yayd.era.food.ordering.project.restaurant.service.data.access.restaurant.entity;

import in.yayd.era.food.ordering.project.domain.valueobject.OrderApprovalStatus;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_approval", schema = "restaurant")
@Entity
public class OrderApprovalEntity {

    @Id
    private UUID id;

    private UUID restaurantId;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus status;

}
