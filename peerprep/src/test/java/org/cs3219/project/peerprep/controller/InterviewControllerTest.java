package org.cs3219.project.peerprep.controller;

import org.cs3219.project.peerprep.model.dto.interview.InterviewDetailsRequest;
import org.cs3219.project.peerprep.model.dto.interview.InterviewDetailsResponse;
import org.cs3219.project.peerprep.service.InterviewService;
import org.junit.jupiter.api.DisplayName;
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
public class InterviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterviewService interviewService;

    @Test
    @DisplayName("Unit Test getInterviewDetails()")
    void getInterviewDetails() throws Exception {
        Mockito.when(interviewService.getInterviewDetails(InterviewDetailsRequest.builder().userId(123L).difficulty(0).build()))
                .thenReturn(InterviewDetailsResponse.builder().questionId(1L).question("dummy").build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/interview/question?userId=123&difficulty=0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.questionId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.question").value("dummy"))
                .andReturn();
    }
}
