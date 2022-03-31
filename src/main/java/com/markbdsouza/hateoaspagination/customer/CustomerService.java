package com.markbdsouza.hateoaspagination.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> fetchCustomerList() {
        return customerRepository.findAll();
    }

    public List<Customer> fetchCustomerListFiltered(String firstNameFilter, String lastNameFilter) {
        return customerRepository.findAllByFirstNameContainingAndLastNameContaining(firstNameFilter, lastNameFilter);
    }

    public Page<Customer> fetchCustomers(String firstNameFilter, String lastNameFilter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByFirstNameContainingAndLastNameContaining(firstNameFilter, lastNameFilter, pageable);
        return customerPage;
    }

    public Page<Customer> fetchCustomersSorted(String firstName, String lastName, int page, int size, List<String> sortList) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, null)));
        Page<Customer> customerPage = customerRepository.findAllByFirstNameContainingAndLastNameContaining(firstName, lastName, pageable);
        return customerPage;
    }

    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {
        List<Sort.Order> sorts = new ArrayList<>();
        Sort.Direction direction;
        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.DESC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
