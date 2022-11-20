package com.epam.epmcacm.msademo.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Objects;

public class AddRequestHeaderGatewayFilterFactory extends AbstractNameValueGatewayFilterFactory {

    private static final String TRACE_ID_HEADER_NAME = "X-B3-TraceId";

    private static final String SPAN_ID_HEADER_NAME = "X-B3-SpanId";

    @Autowired
    private Tracer tracer;

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (Objects.nonNull(tracer.currentTraceContext()) && Objects.nonNull(tracer.currentTraceContext().context())) {
                request.mutate()
                        .header(TRACE_ID_HEADER_NAME, tracer.currentTraceContext().context().traceId())
                        .header(SPAN_ID_HEADER_NAME, tracer.currentTraceContext().context().spanId())
                        .build();
            }

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

}
