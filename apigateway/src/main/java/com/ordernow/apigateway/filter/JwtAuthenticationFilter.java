package com.ordernow.apigateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.ordernow.apigateway.security.JwtService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();
        HttpMethod method = request.getMethod();

        // Allow CORS preflight requests
        if (HttpMethod.OPTIONS.equals(method)) {
            return chain.filter(exchange);
        }

        // Allow public endpoints
        if (isPublicEndpoint(path, method)) {
            return chain.filter(exchange);
        }

        // Read Authorization header
        String authHeader = request.getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7).trim();

        if (token.isEmpty() || !jwtService.isTokenValid(token)) {
            return unauthorized(exchange);
        }

        // Extract authenticated user details
        String username = jwtService.extractUsername(token);
        String userId = jwtService.extractUserId(token);

        // Validate required JWT claims
        if (username == null || username.isBlank()
                || userId == null || userId.isBlank()) {
            return unauthorized(exchange);
        }

        // Remove client-supplied identity headers
        // and set trusted values from JWT
        ServerHttpRequest modifiedRequest = request.mutate()
                .headers(headers -> {
                    headers.remove("X-Authenticated-User");
                    headers.remove("X-Authenticated-User-Id");

                    headers.set("X-Authenticated-User", username);
                    headers.set("X-Authenticated-User-Id", userId);
                })
                .build();

        return chain.filter(
                exchange.mutate()
                        .request(modifiedRequest)
                        .build());
    }

    private boolean isPublicEndpoint(String path, HttpMethod method) {

        // Public registration and login
        if (HttpMethod.POST.equals(method)
                && (path.equals("/api/v1/auth/register")
                || path.equals("/api/v1/auth/login"))) {
            return true;
        }

        // Public hotel listing and menu viewing
        if (HttpMethod.GET.equals(method)
                && (path.equals("/api/v1/hotels")
                || path.startsWith("/api/v1/hotels/"))) {
            return true;
        }
     // Public documentation and health endpoint (GET only)
        if (HttpMethod.GET.equals(method)) {
            return path.startsWith("/swagger-ui/")
                    || path.equals("/swagger-ui.html")
                    || path.equals("/api-docs")
                    || path.startsWith("/api-docs/")
                    || path.startsWith("/v3/api-docs/")
                    || path.equals("/actuator/health");
        }

        return false;

       
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}