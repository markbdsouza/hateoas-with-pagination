package com.markbdsouza.hateoaspagination.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerModelAssembler customerModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Customer> pagedResourcesAssembler;

    @GetMapping("/api/v0/customers")
    public List<Customer> fetchCustomersAsList() {
        return customerService.fetchCustomerList();
    }

    @GetMapping("/api/v1/customers")
    public List<Customer> fetchCustomersAsFilteredList(@RequestParam(defaultValue = "") String firstName,
                                                       @RequestParam(defaultValue = "") String lastName) {
        return customerService.fetchCustomerListFiltered(firstName, lastName);
    }

    @GetMapping("/api/v2/customers")
    public Page<Customer> fetchCustomersWithPageInterface(@RequestParam(defaultValue = "") String firstName,
                                                          @RequestParam(defaultValue = "") String lastName,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "30") int size) {
        return customerService.fetchCustomers(firstName, lastName, page, size);
    }

    @GetMapping("/api/v3/customers")
    public Page<Customer> fetchCustomersWithPageInterfaceAndSorted(@RequestParam(defaultValue = "") String firstName,
                                                                   @RequestParam(defaultValue = "") String lastName,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "30") int size,
                                                                   @RequestParam(defaultValue = "") List<String> sortList) {
        return customerService.fetchCustomersSorted(firstName, lastName, page, size, sortList);
    }


    @GetMapping("/api/v4/customers")
    public PagedModel<CustomerModel> fetchCustomersWithPagination(
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size) {
        // uses hateoas
        Page<Customer> customerPage = customerService.fetchCustomers(firstName, lastName, page, size);
        return pagedResourcesAssembler
                .toModel(customerPage, customerModelAssembler);
    }
}
