package in.yayd.era.food.ordering.project.customer.service.domain.entity;

import in.yayd.era.food.ordering.project.domain.entity.AggregateRoot;
import in.yayd.era.food.ordering.project.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    private final String username;
    private final String firstName;
    private final String lastName;

    public Customer(CustomerId customerId, String username, String firstName, String lastName) {
        super.setId(customerId);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
