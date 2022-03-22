package com.dev.explore.spring.springbootmonitor.aop;

import kamon.Kamon;
import kamon.trace.Span;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        String uri = request.getURI().getPath();
        String method = request.getMethodValue();
        String name = "REST call [" + method + "] " + uri;

        log.debug("Intercepted Rest call: {}", name);

        Span span = Kamon.spanBuilder(name)
                .name(name)
                .tag("http.method", method)
                .tag("http.url", request.getURI().toString())
                .start();

        return Kamon.runWithSpan(span, true, () -> execute(request, body, execution));
    }

    private ClientHttpResponse execute(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) {
        try {
            return execution.execute(request, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
