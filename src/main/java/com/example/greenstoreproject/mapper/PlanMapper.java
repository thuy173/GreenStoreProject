package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.plan.PlanRequest;
import com.example.greenstoreproject.bean.response.plan.PlanResponse;
import com.example.greenstoreproject.bean.response.plan.ScheduleProductResponse;
import com.example.greenstoreproject.bean.response.plan.ScheduleResponse;
import com.example.greenstoreproject.entity.Plan;
import com.example.greenstoreproject.entity.Schedule;
import com.example.greenstoreproject.entity.ScheduleProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    Plan toEntity(PlanRequest dto);

    PlanResponse toResponseDTO(Plan entity);

    ScheduleResponse toResponseDTO(Schedule entity);

    @Mapping(target = "productId", source = "comboProduct.product.productId")
    @Mapping(target = "productName", source = "comboProduct.product.productName")
    ScheduleProductResponse toResponseDTO(ScheduleProduct entity);
}
