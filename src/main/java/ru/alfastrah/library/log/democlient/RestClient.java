package ru.alfastrah.library.log.democlient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestClient {
    static final String HOST = "http://localhost:8080";
    private final RestTemplate restTemplate;

    String health() {
        return restTemplate.getForEntity(HOST + "/health", String.class).getBody();
    }

    String string(String param, String body) {
        return restTemplate.postForObject(HOST + "/string?param=" + param, body, String.class);
    }

    Holder holder(String param, Holder body) {
        return restTemplate.postForObject(HOST + "holder?param=" + param, body, Holder.class);
    }

}
