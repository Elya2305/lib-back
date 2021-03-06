package com.lib.utils;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@AllArgsConstructor
public abstract class AbstractHttpClient implements RestHttpClient {
    private final RestTemplate restTemplate;

    @Override
    public String get(String url) {
        log.info("Get by url {}", url);
        try {
            HttpEntity<String> request = new HttpEntity<>(headers());
            return restTemplate.exchange(url, HttpMethod.GET, request, String.class).getBody();
        } catch (HttpClientErrorException e) {
            log.error("Error while processing get request", e);
            throw e;
        }
    }

    @Override
    public <T> T get(String url, Class<T> response) {
        log.info("Get by url {}", url);
        try {
            HttpEntity<T> request = new HttpEntity<>(headers());
            return restTemplate.exchange(url, HttpMethod.GET, request, response).getBody();
        } catch (HttpClientErrorException e) {
            log.error("Error while processing get request", e);
            throw e;
        }
    }

    @Override
    public <T, R> T post(String url, R request, Class<T> response) {
        try {
            log.info("Post to {}", url);
            HttpEntity<R> requestEntity = new HttpEntity<>(request, headers());
            return restTemplate.exchange(url, HttpMethod.POST, requestEntity, response).getBody();
        } catch (HttpClientErrorException e) {
            log.error("Error while processing post request", e);
            throw e;
        }
    }

    public abstract HttpHeaders headers();
}
