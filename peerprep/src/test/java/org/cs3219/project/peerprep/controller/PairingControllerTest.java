package org.cs3219.project.peerprep.controller;

import org.cs3219.project.peerprep.model.dto.PairingRequest;
import org.cs3219.project.peerprep.model.dto.PairingResponse;
import org.cs3219.project.peerprep.service.PairingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PairingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PairingService pairingService;

    @Test
    void testMatch() throws Exception {
        PairingRequest firstRequest = PairingRequest.builder().userId(123L).difficulty(2).build();
        PairingRequest secondRequest = PairingRequest.builder().userId(234L).difficulty(2).build();

        PairingResponse firstResponse = PairingResponse.builder()
                .userId(firstRequest.getUserId())
                .difficulty(firstRequest.getDifficulty())
                .peerId(secondRequest.getUserId())
                .interviewer(1)
                .build();
        PairingResponse secondResponse = PairingResponse.builder()
                .userId(secondRequest.getUserId())
                .difficulty(secondRequest.getDifficulty())
                .peerId(firstRequest.getUserId())
                .interviewer(0)
                .build();

        Mockito.when(pairingService.getPairingResult(firstRequest))
                .thenReturn(firstResponse);
        Mockito.when(pairingService.getPairingResult(secondRequest))
                .thenReturn(secondResponse);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/match/queue?id=123&difficulty=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.difficulty").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.peer_id").value(234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.interviewer").value(1))
                .andReturn();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/match/queue?id=234&difficulty=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(234))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.difficulty").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.peer_id").value(123))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.interviewer").value(0))
                .andReturn();
    }

    @Test
    public void testUnmatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/match/dequeue?id=123&difficulty=2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }
}
