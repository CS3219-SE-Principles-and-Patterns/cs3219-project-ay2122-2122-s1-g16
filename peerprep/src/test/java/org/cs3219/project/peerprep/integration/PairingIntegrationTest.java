package org.cs3219.project.peerprep.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.cs3219.project.peerprep.service.Pairing.MatchMaking;
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
public class PairingIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private String baseUrl = "/api/v1/match/";

    private String matchUrl = baseUrl + "queue?id={id}&difficulty={difficulty}";

    private String unmatchUrl = baseUrl + "dequeue?id={id}&difficulty={difficulty}";

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
        synchronized (MatchMaking.class) {
            // MatchMaking.reset();
            ThreadPoolExecutor executor =
                    (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
            SoftAssertions softly = new SoftAssertions();

            executor.submit(() -> {
                try {
                    mvc.perform(MockMvcRequestBuilders
                            .get(matchUrl, 1, 0)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(0))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(1))
                            .andReturn();
                } catch (Exception e) {
                    softly.fail("Interrupted");
                }
            });
            executor.submit(() -> {
                try {
                    mvc.perform(MockMvcRequestBuilders
                            .get(matchUrl, 2, 0)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(0))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(2))
                            .andReturn();
                } catch (Exception e) {
                    softly.fail("Interrupted");
                }
            });
            softly.assertAll();
        }
    }

    @Test
    public void testFifo() {
        synchronized (MatchMaking.class) {
            // MatchMaking.reset();
            ThreadPoolExecutor executor =
                    (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
            SoftAssertions softly = new SoftAssertions();

            executor.submit(() -> {
                try {
                    mvc.perform(MockMvcRequestBuilders
                            .get(matchUrl, 123, 0)
                            .accept(MediaType.APPLICATION_JSON))
                            .andReturn();
                    Thread.sleep(1000);
                    mvc.perform(MockMvcRequestBuilders
                            .get(unmatchUrl, 123, 0)
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
                            .get(matchUrl, 2, 0)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(0))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(1))
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
                            .get(matchUrl, 1, 0)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").value(0))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.peer_id").value(2))
                            .andExpect(MockMvcResultMatchers.jsonPath("$.interviewer").value(0))
                            .andReturn();
                } catch (Exception e) {
                    softly.fail("Interrupted");
                }
            });
            softly.assertAll();
        }
    }
}
