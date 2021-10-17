package org.cs3219.project.peerprep.controller;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
@AutoConfigureMockMvc
public class PairingControllerTest {

    @Autowired
    private MockMvc mvc;

    private static String baseUrl = "/api/v1/queue/";

    private static String matchUrl = baseUrl + "match?id={id}&difficulty={difficulty}";

    @Test
    public void testInvalidDifficulty() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(matchUrl, 1, 5)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus())
                .isEqualTo(404);
    }

    @Test
    public void testMatching() {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        SoftAssertions softly = new SoftAssertions();

        executor.submit(() -> {
            try {
                mvc.perform(MockMvcRequestBuilders
                        .get(matchUrl, 20170, 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(20170))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(11993))
                        .andReturn();
            } catch (Exception e) {
                softly.fail("Interrupted");
            }
        });
        executor.submit(() -> {
            try {
                mvc.perform(MockMvcRequestBuilders
                        .get(matchUrl, 11993, 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11993))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(20170))
                        .andReturn();
            } catch (Exception e) {
                softly.fail("Interrupted");
            }
        });
    }
}
