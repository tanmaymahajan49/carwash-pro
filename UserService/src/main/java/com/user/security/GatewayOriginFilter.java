package com.user.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GatewayOriginFilter extends OncePerRequestFilter {

    @Value("${gateway.secret}")
    private String expectedSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String headerSecret = request.getHeader("X-GATEWAY-SECRET");

        if (headerSecret == null || !headerSecret.equals(expectedSecret)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied: Gateway only");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
