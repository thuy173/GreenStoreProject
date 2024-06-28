package com.example.greenstoreproject.repository;

import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.entity.Address;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomer(Customers customer);

    @Query("SELECT a FROM Address a JOIN a.customer c WHERE c.customerId = :customerId")
    List<Address> findByCustomerId(@Param("customerId") Long customerId);
}
