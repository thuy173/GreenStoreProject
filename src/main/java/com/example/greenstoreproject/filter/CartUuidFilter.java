package com.example.greenstoreproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CartUuidFilter implements Filter {
    private static final String CART_UUID_COOKIE_NAME = "CART_UUID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String cartUuid = getCartUuidFromCookies(httpRequest);

        if (cartUuid == null) {
            cartUuid = UUID.randomUUID().toString();
            Cookie cartUuidCookie = new Cookie(CART_UUID_COOKIE_NAME, cartUuid);
            cartUuidCookie.setPath("/");
            cartUuidCookie.setHttpOnly(false);
            cartUuidCookie.setSecure(false);
            cartUuidCookie.setMaxAge(60 * 60 * 24 * 365);

            httpResponse.addCookie(cartUuidCookie);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getCartUuidFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CART_UUID_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
