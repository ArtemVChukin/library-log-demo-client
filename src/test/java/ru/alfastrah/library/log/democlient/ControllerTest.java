package ru.alfastrah.library.log.democlient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RestClient restClient;

    @Test
    void healthTest() throws Exception {
        String response = "ok";
        when(restClient.health()).thenReturn(response);
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void stringTest() throws Exception {
        String param = "param";
        String body = "body";
        String response = param + " " + body;
        when(restClient.string(param, body)).thenReturn(response);
        mockMvc.perform(get("/string")
                .param("param", param)
                .param("body", body))
                .andExpect(status().isOk())
                .andExpect(content().string(response));
    }

    @Test
    void holderTest() throws Exception {
        String param = "param";
        Holder body = new Holder("body");
        Holder response = new Holder(param + " " + body.getValue());
        when(restClient.holder(eq(param), argThat(samePropertyValuesAs((body))))).thenReturn(response);
        mockMvc.perform(get("/holder")
                .param("param", param)
                .param("body", body.getValue()))
                .andExpect(status().isOk())
                .andExpect(content().string(response.getValue()));
    }
}