package com.example.greenstoreproject.service.impl;

import com.example.greenstoreproject.bean.request.cartItem.CartItemRequest;
import com.example.greenstoreproject.bean.response.cart.CartResponse;
import com.example.greenstoreproject.entity.CartItems;
import com.example.greenstoreproject.entity.Carts;
import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.entity.Products;
import com.example.greenstoreproject.mapper.CartMapper;
import com.example.greenstoreproject.repository.CartItemRepository;
import com.example.greenstoreproject.repository.CartRepository;
import com.example.greenstoreproject.repository.CustomerRepository;
import com.example.greenstoreproject.repository.ProductRepository;
import com.example.greenstoreproject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CartResponse getCartByCustomerId(Long customerId) {
        if (!hasAccess(customerId)) {
            throw new RuntimeException("Access Denied");
        }

        Carts cart = cartRepository.findByCustomerCustomerId(customerId);
        if (cart == null) {
            cart = new Carts();
            Customers customer = new Customers();
            customer.setCustomerId(customerId);
            cart.setCustomer(customer);
            cart = cartRepository.save(cart);
        }
        return cartMapper.mapToCartResponse(cart);
    }

    @Override
    public CartResponse addItemToCart(Long customerId, CartItemRequest cartItemRequest) {
        if (!hasAccess(customerId)) {
            throw new RuntimeException("Access Denied");
        }

        Carts carts = cartRepository.findByCustomerCustomerId(customerId);

        if(carts == null) {
            carts = new Carts();
            Customers customer = new Customers();
            customer.setCustomerId(customerId);
            carts.setCustomer(customer);
        }
        Products product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantityInStock() <= 0) {
            throw new RuntimeException("Product is out of stock");
        }

        Optional<CartItems> existingCartItem = carts.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(cartItemRequest.getProductId()))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItems cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemRequest.getQuantity());
        } else {
            CartItems newCartItem = new CartItems();
            newCartItem.setCart(carts);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(cartItemRequest.getQuantity());
            carts.getCartItems().add(newCartItem);
        }


        cartRepository.save(carts);

        return cartMapper.mapToCartResponse(carts);
    }

    @Override
    public CartResponse removeItemFromCart(Long userId, Long cartItemId) {
        if (!hasAccess(userId)) {
            throw new RuntimeException("Access Denied");
        }

        Carts cart = cartRepository.findByCustomerCustomerId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartItems cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return cartMapper.mapToCartResponse(cart);
    }

    @Override
    public CartResponse updateItemQuantity(Long userId, Long cartItemId, int quantity) {
        if (!hasAccess(userId)) {
            throw new RuntimeException("Access Denied");
        }

        Carts cart = cartRepository.findByCustomerCustomerId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartItems cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cartMapper.mapToCartResponse(cart);
    }

    @Override
    public void clearCart(Long userId) {
        if (!hasAccess(userId)) {
            throw new RuntimeException("Access Denied");
        }

        Carts cart = cartRepository.findByCustomerCustomerId(userId);
        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    public boolean hasAccess(Long customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        String currentUsername = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return true;
        }

        Customers currentCustomer = customerRepository.findByEmail(currentUsername);
        return currentCustomer != null && currentCustomer.getCustomerId().equals(customerId);
    }
}
