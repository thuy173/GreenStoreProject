package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.address.AddressRequest;
import com.example.greenstoreproject.bean.request.category.CategoryRequest;
import com.example.greenstoreproject.bean.response.address.AddressResponse;
import com.example.greenstoreproject.entity.Address;
import com.example.greenstoreproject.entity.Categories;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public static AddressResponse convertToResponse(Address entity) {
        AddressResponse response = new AddressResponse();
        response.setAddress(buildAddressString(entity));
        response.setAddressDetail(entity.getAddressDetail());
        return response;
    }

    public static Address convertToEntity(AddressRequest request) {
        Address entity = new Address();
        entity.setProvince(request.getProvince());
        entity.setDistrict(request.getDistrict());
        entity.setWard(request.getWard());
        entity.setAddressDetail(request.getAddressDetail());
        return entity;
    }

    public static void updateFromRequest(Address address, AddressRequest addressRequest) {
        address.setProvince(addressRequest.getProvince());
        address.setDistrict(addressRequest.getDistrict());
        address.setWard(addressRequest.getWard());
        address.setAddressDetail(addressRequest.getAddressDetail());

    }

    private static String buildAddressString(Address entity) {
        return entity.getProvince() + ", " + entity.getDistrict() + ", " + entity.getWard();
    }
}
