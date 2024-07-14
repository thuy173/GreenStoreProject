package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.voucher.VoucherRequest;
import com.example.greenstoreproject.bean.response.voucher.VoucherResponse;
import com.example.greenstoreproject.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    Voucher toEntity(VoucherRequest voucherRequest);

    VoucherResponse toDto(Voucher voucher);

    void updateEntityFromDto(VoucherRequest voucherRequest, @MappingTarget Voucher voucher);

}
