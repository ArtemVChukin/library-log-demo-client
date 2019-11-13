package ru.alfastrah.library.log.democlient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
    private final RestClient restClient;

    @GetMapping("/health")
    public String health() {
        return restClient.health();
    }

    @GetMapping("/string")
    public String stringBody(String param, String body) {
        return restClient.string(param, body);
    }

    @GetMapping("/holder")
    public String holderBody(String param, String body) {
        return restClient.holder(param, new Holder(body)).getValue();
    }
}
