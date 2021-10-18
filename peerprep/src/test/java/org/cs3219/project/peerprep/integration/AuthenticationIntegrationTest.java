package org.cs3219.project.peerprep.integration;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerAndActivate() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test1\", \"email\": \"test1@gmail.com\", \"password\": \"test1_password\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("test1@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("test1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty())
                .andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account/activate")
                        .param("email", "test1@gmail.com")
                        .param("token", token))
                .andExpect(status().isOk());
    }

    @Test
    void resetPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test2\", \"email\": \"test2@gmail.com\", \"password\": \"test2_password\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account/password/pre-reset")
                        .param("email", "test2@gmail.com"))
                .andExpect(status().isOk())
                .andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account/password/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"email\": \"test2@gmail.com\", \"password\": \"12345678\", \"token\": \"%s\"}", token))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
