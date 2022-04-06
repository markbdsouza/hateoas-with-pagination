package com.markbdsouza.hateoaspagination.customer;

import org.springframework.hateoas.RepresentationModel;

/**
 * The CustomerModel class extends the Hateoas Representation Model and is required if we want to convert the Customer
 * Entity to a pagination format
 */
public class CustomerModel extends RepresentationModel<CustomerModel> {
    private Long id;
    private String customerId;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
