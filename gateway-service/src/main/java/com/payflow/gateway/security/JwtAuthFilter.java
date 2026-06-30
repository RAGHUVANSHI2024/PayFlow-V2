package com.payflow.gateway.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getPath();

        // if the public endpoint are matched than we will bypass the filter
        if (PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith)){
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("Auth Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer")){
            log.info("AuthHeader is null or does not start with Bearer !!");
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try{
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            log.info("TOKEN = {}", token);
            String email = jwtUtil.extractEmail(token);

            if (!jwtUtil.isTokenValid(token,email)){
                log.info("Token is not validate check expiration and secret key :");
                exchange.getResponse()
                        .setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            Long userId = jwtUtil.extractUserId(token);

            String role = jwtUtil.extractRole(token);

            log.info("UserId: {}, Role: {}", userId, role);

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", String.valueOf(userId))
                    .header("X-User-Email", email)
                    .header("X-User-Role", role)
                    .build();

            // we are sending the header data to the down services
            ServerWebExchange mutatedExchange = exchange.mutate()
                    .request(mutatedRequest)
                    .build();

            return chain.filter(mutatedExchange);
        }
        catch (Exception e) {
            log.error("JWT Exception", e);
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }
    }
}
