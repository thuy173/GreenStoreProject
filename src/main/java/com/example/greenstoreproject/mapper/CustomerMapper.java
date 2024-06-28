package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.entity.Address;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CustomerMapper {
    public static CustomerResponse convertToResponse(Customers customers) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setFullName(customers.getFirstName() + " " + customers.getLastName());
        customerResponse.setEmail(customers.getEmail());
        customerResponse.setPhoneNumber(customers.getPhoneNumber());
        customerResponse.setAddress(convertAddressIsActiveList(customers.getAddress()));

        return customerResponse;
    }

    public static CustomerResponse convertToDetailResponse(Customers customers) {

        CustomerResponse customerResponse = new CustomerResponse();

        if (customers == null) {
            return customerResponse;
        }
        customerResponse.setFullName(customers.getFirstName() + " " + customers.getLastName());
        customerResponse.setEmail(customers.getEmail());
        customerResponse.setPhoneNumber(customers.getPhoneNumber());
        customerResponse.setAddress(convertAddressList(customers.getAddress()));

        return customerResponse;
    }

    public static void updateFromRequest(Customers customers, CustomerUpdateRequest customerUpdateRequest) {
        customers.setFirstName(customerUpdateRequest.getFirstName());
        customers.setLastName(customerUpdateRequest.getLastName());
        customers.setEmail(customerUpdateRequest.getEmail());
        customers.setPhoneNumber(customerUpdateRequest.getPhoneNumber());

    }

    private static List<AddressResponse> convertAddressIsActiveList(List<Address> addresses) {
        return addresses.stream()
//                .filter(address -> address.getIsActive() != null && address.getIsActive())
                .filter(address -> Boolean.TRUE.equals(address.getIsActive()))
                .map(AddressMapper::convertToResponse)
                .collect(Collectors.toList());
    }
    private static List<AddressResponse> convertAddressList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressMapper::convertToResponse)
                .collect(Collectors.toList());
    }
}
