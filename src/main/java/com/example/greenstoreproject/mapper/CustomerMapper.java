package com.example.greenstoreproject.mapper;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateAvatarRequest;
import com.example.greenstoreproject.bean.request.customer.CustomerUpdateRequest;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.bean.response.customer.CustomerResponse;
import com.example.greenstoreproject.entity.Address;
import com.example.greenstoreproject.entity.Customers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class CustomerMapper {
    public static CustomerResponse convertToResponse(Customers customers) {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setCustomerId(customers.getCustomerId());
        customerResponse.setFullName(customers.getFirstName() + " " + customers.getLastName());
        customerResponse.setEmail(customers.getEmail());
        customerResponse.setPhoneNumber(customers.getPhoneNumber());
        customerResponse.setAvatar(customers.getAvatar());
        customerResponse.setAddress(convertAddressIsActiveList(customers.getAddress()));

        return customerResponse;
    }

    public static CustomerResponse convertToDetailResponse(Customers customers) {

        CustomerResponse customerResponse = new CustomerResponse();

        if (customers == null) {
            return customerResponse;
        }
        customerResponse.setCustomerId(customers.getCustomerId());
        customerResponse.setFullName(customers.getFirstName() + " " + customers.getLastName());
        customerResponse.setEmail(customers.getEmail());
        customerResponse.setPhoneNumber(customers.getPhoneNumber());
        customerResponse.setAvatar(customers.getAvatar());
        customerResponse.setAddress(convertAddressList(customers.getAddress()));
        customerResponse.setPoints(customers.getPoints());

        return customerResponse;
    }

    public static void updateFromRequest(Customers customers, CustomerUpdateRequest customerUpdateRequest) {
        customers.setFirstName(customerUpdateRequest.getFirstName());
        customers.setLastName(customerUpdateRequest.getLastName());
        customers.setEmail(customerUpdateRequest.getEmail());
        customers.setPhoneNumber(customerUpdateRequest.getPhoneNumber());

    }

    public static void updateAvatar(Cloudinary cloudinary, Customers customers, CustomerUpdateAvatarRequest customerUpdateAvatarRequest){
        try {
            Map uploadResult = cloudinary.uploader().upload(customerUpdateAvatarRequest.getAvatar().getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("url").toString();
            customers.setAvatar(url);
        } catch (IOException e) {
            throw new RuntimeException("Avatar upload failed", e);
        }
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
