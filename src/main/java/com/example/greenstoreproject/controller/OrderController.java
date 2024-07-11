package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.order.OrderRequest;
import com.example.greenstoreproject.bean.response.order.OrderCustomerResponse;
import com.example.greenstoreproject.bean.response.order.OrderDetailResponse;
import com.example.greenstoreproject.bean.response.order.OrderResponse;
import com.example.greenstoreproject.entity.OrderStatus;
import com.example.greenstoreproject.service.OrderService;
import com.example.greenstoreproject.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    private final OrderService orderService;
    private final AuthServiceImpl authService;

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/myOrders")
    public ResponseEntity<List<OrderCustomerResponse>> getAllOrdersByCurrentCustomer() {
        List<OrderCustomerResponse> orders = orderService.getAllOrdersByCurrentCustomer();
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}")
    public OrderResponse updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        return orderService.updateOrder(orderId, orderRequest);
    }

    @PutMapping("/{orderId}/status")
    public OrderResponse updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam("status") OrderStatus status) {
        boolean isAdmin = authService.isAdmin();
        return orderService.updateOrderStatus(orderId, status, isAdmin);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }
}
