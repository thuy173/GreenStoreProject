package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.address.AddressRequest;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.entity.Address;
import com.example.greenstoreproject.entity.Categories;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.exception.EmptyException;
import com.example.greenstoreproject.exception.NotFoundException;
import com.example.greenstoreproject.mapper.AddressMapper;
import com.example.greenstoreproject.mapper.CategoryMapper;
import com.example.greenstoreproject.repository.AddressRepository;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.service.AddressService;
import com.example.greenstoreproject.util.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    private Customers getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customers customer = customerRepository.findByEmail(userDetails.getUsername());
        if (customer == null) {
            throw new NotFoundException("User not found");
        }
        return customer;
    }

    @Override
    public List<AddressResponse> getAllAddress() {
        Customers currentUser = getCurrentUser();
        List<Address> addresses = addressRepository.findByCustomer(currentUser);
        if (addresses.isEmpty()) {
            throw new EmptyException("Address list is empty");
        }
        return addresses.stream()
                .map(AddressMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse getAddressById(Long id) {
        Customers currentUser = getCurrentUser();
        Address address = addressRepository.findById(id)
                .filter(a -> a.getCustomer().equals(currentUser))
                .orElseThrow(() -> new NotFoundException("Address not found or you don't have access to it: " + id));
        return AddressMapper.convertToResponse(address);
    }

    @Override
    public String createAddress(AddressRequest addressRequest) {
        Customers currentUser = getCurrentUser();
        Address address = AddressMapper.convertToEntity(addressRequest);
        address.getCustomer().add(currentUser);
        currentUser.getAddress().add(address);
        addressRepository.save(address);
        return SuccessMessage.SUCCESS_CREATED.getMessage();
    }

    @Override
    public String updateAddress(Long id, AddressRequest addressRequest) {
        Customers currentUser = getCurrentUser();
        Address address = addressRepository.findById(id)
                .filter(a -> a.getCustomer().equals(currentUser))
                .orElseThrow(() -> new NotFoundException("Address not found or you don't have access to it: " + id));
        AddressMapper.updateFromRequest(address, addressRequest);
        addressRepository.save(address);
        return SuccessMessage.SUCCESS_UPDATED.getMessage();
    }

    @Override
    public String deleteAddress(Long id) {
        Customers currentUser = getCurrentUser();
        Address address = addressRepository.findById(id)
                .filter(a -> a.getCustomer().equals(currentUser))
                .orElseThrow(() -> new NotFoundException("Address not found or you don't have access to it: " + id));
        addressRepository.delete(address);
        return SuccessMessage.SUCCESS_DELETED.getMessage();
    }
}
