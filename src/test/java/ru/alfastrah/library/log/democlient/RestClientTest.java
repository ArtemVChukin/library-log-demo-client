package ru.alfastrah.library.log.democlient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static ru.alfastrah.library.log.democlient.RestClient.HOST;

@SpringBootTest
class RestClientTest {

    @Autowired
    RestClient restClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ObjectMapper mapper;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).bufferContent().build();
    }

    @Test
    void health() {
        //given
        String response = "ok";
        String url = "/health";
        String fullUrl = HOST + url;
        mockServer.expect(method(HttpMethod.GET))
                .andExpect(requestTo(fullUrl))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        //then
        String restClientResponse = restClient.health();

        //when
        assertEquals(response, restClientResponse);
    }

    @Test
    void string() {
        //given
        String param = "param";
        String body = "body";
        String response = param + " " + body;
        String url = "/string";
        String fullUrl = HOST + url + "?param=" + param;
        mockServer.expect(method(HttpMethod.POST))
                .andExpect(requestTo(fullUrl))
                .andExpect(queryParam("param", param))
                .andExpect(content().string(body))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        //when
        String restClientResponse = restClient.string(param, body);

        //then
        assertEquals(response, restClientResponse);
    }

    @Test
    void holder() throws JsonProcessingException {
        //given
        String param = "param";
        Holder body = new Holder("body");
        Holder response = new Holder(param + " " + body);
        String url = "/holder";
        String fullUrl = HOST + url + "?param=" + param;
        mockServer.expect(method(HttpMethod.POST))
                .andExpect(requestTo(fullUrl))
                .andExpect(queryParam("param", param))
                .andExpect(content().string(mapper.writeValueAsString(body)))
                .andRespond(withSuccess(mapper.writeValueAsString(response), MediaType.APPLICATION_JSON));
        //when
        Holder restClientResponse = restClient.holder(param, body);

        //then
        assertEquals(response.getValue(), restClientResponse.getValue());
    }
}