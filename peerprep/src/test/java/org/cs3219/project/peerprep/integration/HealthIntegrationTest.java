package org.cs3219.project.peerprep.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HealthIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloWorldAtRoot() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("Hello World at Root!", result.getResponse().getContentAsString()));
    }

    @Test
    void helloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals("Hello World!", result.getResponse().getContentAsString()));
    }
}