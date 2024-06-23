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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CartResponse getCartByCustomerIdOrUuid(Long customerId, String cartUuid) {
        Carts cart = null;

        if (customerId != null) {
            if (!hasAccess(customerId)) {
                throw new RuntimeException("Access Denied");
            }
            cart = cartRepository.findByCustomerCustomerId(customerId);
        } else if (cartUuid != null) {
            cart = cartRepository.findByCartUuid(cartUuid);
        }

        if (cart == null) {
            cart = new Carts();
            cart.setCartUuid(cartUuid != null ? cartUuid : UUID.randomUUID().toString());
            if (customerId != null) {
                Customers customer = new Customers();
                customer.setCustomerId(customerId);
                cart.setCustomer(customer);
            }
            cart = cartRepository.save(cart);
        }
        return cartMapper.mapToCartResponse(cart);
    }

    @Override
    public CartResponse addItemToCart(Long customerId, String cartUuid, CartItemRequest cartItemRequest) {
        Carts carts = null;

        if (customerId != null) {
            if (!hasAccess(customerId)) {
                throw new RuntimeException("Access Denied");
            }
            carts = cartRepository.findByCustomerCustomerId(customerId);
        } else if (cartUuid != null) {
            carts = cartRepository.findByCartUuid(cartUuid);
        }

        if (carts == null) {
            carts = new Carts();
            carts.setCartUuid(cartUuid != null ? cartUuid : UUID.randomUUID().toString());
            if (customerId != null) {
                Customers customer = new Customers();
                customer.setCustomerId(customerId);
                carts.setCustomer(customer);
            }
            carts = cartRepository.save(carts);
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
    public CartResponse mergeCartUponLogin(Long customerId, String cartUuid) {
        Carts tempCart = cartRepository.findByCartUuid(cartUuid);
        Carts customerCart = cartRepository.findByCustomerCustomerId(customerId);

        if (tempCart == null) {
            if (customerCart == null) {
                customerCart = new Carts();
                customerCart.setCartUuid(UUID.randomUUID().toString());
                Customers customer = new Customers();
                customer.setCustomerId(customerId);
                customerCart.setCustomer(customer);
                customerCart = cartRepository.save(customerCart);
            }
            return cartMapper.mapToCartResponse(customerCart);
        }

        if (customerCart == null) {
            tempCart.setCustomer(customerRepository.findById(customerId).orElse(null));
            tempCart = cartRepository.save(tempCart);
            return cartMapper.mapToCartResponse(tempCart);
        }

        mergeCartItems(customerCart, tempCart.getCartItems());

        cartRepository.delete(tempCart);

        customerCart = cartRepository.save(customerCart);

        return cartMapper.mapToCartResponse(customerCart);
    }

    private void mergeCartItems(Carts customerCart, List<CartItems> tempCartItems) {
        for (CartItems tempItem : tempCartItems) {
            Optional<CartItems> existingItemOpt = customerCart.getCartItems().stream()
                    .filter(item -> item.getProduct().getProductId().equals(tempItem.getProduct().getProductId()))
                    .findFirst();

            if (existingItemOpt.isPresent()) {
                CartItems existingItem = existingItemOpt.get();
                existingItem.setQuantity(existingItem.getQuantity() + tempItem.getQuantity());
            } else {
                CartItems newItem = new CartItems();
                newItem.setCart(customerCart);
                newItem.setProduct(tempItem.getProduct());
                newItem.setQuantity(tempItem.getQuantity());
                customerCart.getCartItems().add(newItem);
            }
        }
    }

    @Override
    public CartResponse removeItemFromCart(Long userId, String cartUuid, Long cartItemId) {
        Carts cart = getCartByUserIdOrUuid(userId, cartUuid);

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
    public CartResponse updateItemQuantity(Long userId, String cartUuid, Long cartItemId, Double quantity) {
        Carts cart = getCartByUserIdOrUuid(userId, cartUuid);

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
    public void clearCart(Long userId, String cartUuid) {
        Carts cart = getCartByUserIdOrUuid(userId, cartUuid);

        if (cart != null) {
            cart.getCartItems().clear();
            cartRepository.save(cart);
        }
    }

    private Carts getCartByUserIdOrUuid(Long userId, String cartUuid) {
        Carts cart = null;
        if (userId != null) {
            if (!hasAccess(userId)) {
                throw new RuntimeException("Access Denied");
            }
            cart = cartRepository.findByCustomerCustomerId(userId);
        } else if (cartUuid != null) {
            cart = cartRepository.findByCartUuid(cartUuid);
        }
        return cart;
    }

    private boolean hasAccess(Long customerId) {
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

