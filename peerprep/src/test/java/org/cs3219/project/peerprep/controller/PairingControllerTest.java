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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PairingControllerTest {

    @Autowired
    private MockMvc mvc;

    private static String baseUrl = "/api/v1/match/";

    private static String matchUrl = baseUrl + "queue?id={id}&difficulty={difficulty}";

    private static String unmatchUrl = baseUrl + "dequeue?id={id}&difficulty={difficulty}";

    @Test
    public void testInvalidDifficulty() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(matchUrl, 1, 5)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        Assertions.assertThat(mvcResult.getResponse().getStatus())
                .isEqualTo(400);
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

    @Test
    public void testFifo() {
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        SoftAssertions softly = new SoftAssertions();

        executor.submit(() -> {
            try {
                mvc.perform(MockMvcRequestBuilders
                        .get(matchUrl, 123, 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
                Thread.sleep(1000);
                mvc.perform(MockMvcRequestBuilders
                        .get(unmatchUrl, 123, 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
            } catch (Exception e) {
                softly.fail("Interrupted");
            }
        });

        executor.submit(() -> {
            try {
                Thread.sleep(2000);
                mvc.perform(MockMvcRequestBuilders
                        .get(matchUrl, 20170, 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(20170))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(11993))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.interviewer").value(1))
                        .andReturn();
            } catch (Exception e) {
                softly.fail("Interrupted");
            }
        });

        executor.submit(() -> {
            try {
                Thread.sleep(2000);
                mvc.perform(MockMvcRequestBuilders
                        .get(matchUrl, 11993, 1)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(11993))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(20170))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.interviewer").value(0))
                        .andReturn();
            } catch (Exception e) {
                softly.fail("Interrupted");
            }
        });
    }
}
