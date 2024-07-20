package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.voucher.VoucherRequest;
import com.example.greenstoreproject.bean.response.voucher.VoucherResponse;
import com.example.greenstoreproject.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "status", source = "status")
    Voucher toEntity(VoucherRequest voucherRequest);

    VoucherResponse toDto(Voucher voucher);

    @Mapping(target = "status", ignore = true)
    void updateEntityFromDto(VoucherRequest voucherRequest, @MappingTarget Voucher voucher);

}
