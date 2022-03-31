package com.markbdsouza.hateoaspagination.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAllByFirstNameContainingAndLastNameContaining(String firstNameFilter, String  lastNameFilter, Pageable pageable);

    List<Customer> findAllByFirstNameContainingAndLastNameContaining(String firstNameFilter, String  lastNameFilter);
}
