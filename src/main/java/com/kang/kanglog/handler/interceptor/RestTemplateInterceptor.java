package com.kang.kanglog.handler.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;


@Slf4j
public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {



    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        log.info("restTemplate request header: " + request.getHeaders().toString());
        log.info("restTemplate request url: " + request.getURI());
        //log.info("restTemplate request body: {}", new String(body, StandardCharsets.UTF_8));
        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
}
