package com.example.greenstoreproject.mapper;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.request.orderItem.OrderItemRequest;
import com.example.greenstoreproject.bean.response.order.OrderCustomerResponse;
import com.example.greenstoreproject.bean.response.order.OrderDetailResponse;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.bean.response.orderItem.OrderItemResponse;
import com.example.greenstoreproject.bean.response.productImage.ProductImageResponse;
import com.example.greenstoreproject.entity.*;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.VoucherRepository;
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
    private final VoucherRepository voucherRepository;

    @Autowired
    private GeoCodingService geoCodingService;

    public OrderResponse toOrderResponse(Orders order) {
        if (order == null) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderCode(order.getOrderCode());
        orderResponse.setCustomerId(order.getCustomer() != null ? order.getCustomer().getCustomerId() : null);
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setLatitude(order.getLatitude());
        orderResponse.setLongitude(order.getLongitude());
        orderResponse.setShippingAddress(order.getShippingAddress());
        orderResponse.setPaymentMethod(order.getPaymentMethod());

        if (order.getVoucher() != null && order.getVoucher().getVoucherId() != null) {
            Voucher voucher = order.getVoucher();
            orderResponse.setVoucherId(voucher.getVoucherId());
        }

        if (order.getCustomer() != null) {
            Customers customer = order.getCustomer();
            String fullName = customer.getFirstName();
            if (customer.getLastName() != null) {
                fullName += " " + customer.getLastName();
            }
            orderResponse.setFullName(fullName);
            orderResponse.setEmail(customer.getEmail());
            orderResponse.setPhoneNumber(customer.getPhoneNumber());
        }

        return orderResponse;
    }

    public OrderCustomerResponse toOrderCustomerResponse(Orders order) {
        if (order == null) {
            return null;
        }

        OrderCustomerResponse orderResponse = new OrderCustomerResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderCode(order.getOrderCode());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setPaymentMethod(order.getPaymentMethod());
        orderResponse.setOrderItems(order.getOrderItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList()));

        if (order.getVoucher() != null && order.getVoucher().getVoucherId() != null) {
            Voucher voucher = order.getVoucher();
            orderResponse.setVoucherId(voucher.getVoucherId());
        }

        return orderResponse;
    }


    public OrderDetailResponse toOrderDetailResponse(Orders order) {
        if (order == null) {
            return null;
        }

        OrderDetailResponse orderResponse = new OrderDetailResponse();
        orderResponse.setOrderId(order.getOrderId());
        orderResponse.setOrderCode(order.getOrderCode());
        orderResponse.setCustomerId(order.getCustomer() != null ? order.getCustomer().getCustomerId() : null);
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setLatitude(order.getLatitude());
        orderResponse.setLongitude(order.getLongitude());
        orderResponse.setShippingAddress(order.getShippingAddress());
        orderResponse.setPaymentMethod(order.getPaymentMethod());
        orderResponse.setOrderItems(order.getOrderItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList()));

        if (order.getVoucher() != null && order.getVoucher().getVoucherId() != null) {
            Voucher voucher = order.getVoucher();
            orderResponse.setVoucherId(voucher.getVoucherId());
        }

        if (order.getCustomer() != null) {
            Customers customer = order.getCustomer();
            String fullName = customer.getFirstName();
            if (customer.getLastName() != null) {
                fullName += " " + customer.getLastName();
            }
            orderResponse.setFullName(fullName);
            orderResponse.setEmail(customer.getEmail());
            orderResponse.setPhoneNumber(customer.getPhoneNumber());
        }

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
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setLatitude(orderRequest.getLatitude());
        order.setLongitude(orderRequest.getLongitude());
        order.setShippingAddress(orderRequest.getShippingAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setOrderItems(orderRequest.getOrderItems().stream()
                .map(this::toOrderItem)
                .collect(Collectors.toList()));

        if (orderRequest.getVoucherId() != null) {
            Voucher voucher = findVoucherById(orderRequest.getVoucherId());
            if (voucher != null && order.getTotalAmount() > voucher.getMinOrderAmount()) {
                order.setVoucher(voucher);
                double discount = order.getTotalAmount() * (voucher.getDiscount() / 100.0);
                double discountedAmount = order.getTotalAmount() - discount;
                order.setTotalAmount(discountedAmount);
            }
        }

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

    private Voucher findVoucherById(Long voucherId) {
        return voucherRepository.findById(voucherId).orElse(null);
    }

    public OrderItemResponse toOrderItemResponse(OrderItems orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setOrderItemId(orderItem.getOrderItemId());
        orderItemResponse.setProductId(orderItem.getProduct().getProductId());
        orderItemResponse.setProductName(orderItem.getProduct().getProductName());
        orderItemResponse.setDescription(orderItem.getProduct().getDescription());
        orderItemResponse.setQuantity(orderItem.getQuantity());
        orderItemResponse.setPrice(orderItem.getProduct().getPrice());
        orderItemResponse.setProductImages(mapToProductImageResponses(orderItem.getProduct().getProductImages()));


        if (orderItem.getQuantity() != null && orderItem.getQuantity() > 0) {
            orderItemResponse.setTotalPrice(orderItem.getQuantity() * orderItem.getProduct().getPrice());
        } else {
            orderItemResponse.setTotalPrice(0.0);
        }
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
