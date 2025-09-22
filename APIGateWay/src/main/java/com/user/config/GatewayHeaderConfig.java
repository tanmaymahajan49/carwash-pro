package com.user.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

@Configuration
public class GatewayHeaderConfig {

    @Bean
    public GlobalFilter customHeaderFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("X-GATEWAY-SECRET", "MyVerySecureSharedSecret")
                    .build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
