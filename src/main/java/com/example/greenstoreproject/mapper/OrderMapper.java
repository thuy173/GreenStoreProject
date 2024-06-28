package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.bean.response.orderItem.OrderItemResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.OrderItems;
import com.example.greenstoreproject.entity.Orders;
import com.example.greenstoreproject.entity.ProductImages;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.service.GeoCodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    @Autowired
    private CustomerRepository customersRepository;

    @Autowired
    private GeoCodingService geoCodingService;

    public OrderResponse toOrderResponse(Orders order) {
        if (order == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setCustomerId(order.getCustomer() != null ? order.getCustomer().getCustomerId() : null);
        orderResponse.setGuestName(order.getGuestName());
        orderResponse.setGuestEmail(order.getGuestEmail());
        orderResponse.setGuestPhone(order.getGuestPhone());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setDiscount(order.getDiscount());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setLatitude(order.getLatitude());
        orderResponse.setLongitude(order.getLongitude());
        orderResponse.setShippingAddress(order.getShippingAddress());


        return orderResponse;
    }

    public OrderResponse toOrderDetailResponse(Orders order) {
        if (order == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setCustomerId(order.getCustomer() != null ? order.getCustomer().getCustomerId() : null);
        orderResponse.setGuestName(order.getGuestName());
        orderResponse.setGuestEmail(order.getGuestEmail());
        orderResponse.setGuestPhone(order.getGuestPhone());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setDiscount(order.getDiscount());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setLatitude(order.getLatitude());
        orderResponse.setLongitude(order.getLongitude());
        orderResponse.setShippingAddress(order.getShippingAddress());
        orderResponse.setOrderItems(order.getOrderItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList()));

        return orderResponse;
    }

    public Orders toOrder(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }

        Orders order = new Orders();
        order.setGuestName(orderRequest.getGuestName());
        order.setGuestEmail(orderRequest.getGuestEmail());
        order.setGuestPhone(orderRequest.getGuestPhone());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setDiscount(orderRequest.getDiscount());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setStatus(orderRequest.getStatus());
        order.setLatitude(orderRequest.getLatitude());
        order.setLongitude(orderRequest.getLongitude());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setOrderItems(orderRequest.getOrderItems().stream()
                .map(this::toOrderItem)
                .collect(Collectors.toList()));

        if (orderRequest.getCustomerId() != null) {
            Customers customer = findCustomerById(orderRequest.getCustomerId());
            order.setCustomer(customer);

            if (customer != null) {
                order.setDefaultShippingAddress();
            }
        }

        if (orderRequest.getLatitude() != 0 && orderRequest.getLongitude() != 0) {
            String address = geoCodingService.getAddressFromCoordinates(orderRequest.getLatitude(), orderRequest.getLongitude());
            order.setShippingAddress(address);
        }

        return order;
    }

    private Customers findCustomerById(Long customerId) {
        return customersRepository.findById(customerId).orElse(null);
    }

    public OrderItemResponse toOrderItemResponse(OrderItems orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setOrderItemId(orderItem.getOrderItemId());
        orderItemResponse.setProductId(orderItem.getProduct().getProductId());
        orderItemResponse.setProductName(orderItem.getProduct().getProductName());
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setPrice(orderItem.getProduct().getPrice());
        orderItemResponse.setProductImages(mapToProductImageResponses(orderItem.getProduct().getProductImages()));

        return orderItemResponse;
    }

    public OrderItems toOrderItem(OrderItemRequest orderItemRequest) {
        if (orderItemRequest == null) {
            return null;
        }

        OrderItems orderItem = new OrderItems();
        orderItem.setQuantity(orderItemRequest.getQuantity());

        return orderItem;
    }

    private List<ProductImageResponse> mapToProductImageResponses(List<ProductImages> productImages) {
        List<ProductImageResponse> productImageResponses = new ArrayList<>();
        if (productImages != null && !productImages.isEmpty()) {
            ProductImages firstProductImage = productImages.get(0);
            ProductImageResponse productImageResponse = new ProductImageResponse();
            productImageResponse.setProductImageId(firstProductImage.getProductImageId());
            productImageResponse.setImageUrl(firstProductImage.getImageUrl());
            productImageResponses.add(productImageResponse);
        }
        return productImageResponses;
    }
}
