package org.cs3219.project.peerprep.integration;

import org.cs3219.project.peerprep.model.entity.User;
import org.cs3219.project.peerprep.model.entity.UserGroup;
import org.cs3219.project.peerprep.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserProfileIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void getUserById() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("test1@gmail.com");
        user.setNickname("test1");
        user.setPassword("password1");
        user.setUserGroup(UserGroup.SIMPLE_USER);
        user.setActivationToken("token1");
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profile/id")
                .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("test1@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("test1"));
    }

    @Test
    void getUserByEmail() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("test1@gmail.com");
        user.setNickname("test1");
        user.setPassword("password1");
        user.setUserGroup(UserGroup.SIMPLE_USER);
        user.setActivationToken("token1");
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/profile/email")
                        .param("email", "test1@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("test1@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("test1"));
    }

    @Test
    void updateNickname() throws Exception {
        User user = new User();
        user.setId(1);
        user.setEmail("test1@gmail.com");
        user.setNickname("test1");
        user.setPassword("password1");
        user.setUserGroup(UserGroup.SIMPLE_USER);
        user.setActivationToken("token1");
        userRepository.save(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"1\",  \"username\": \"hello\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("success"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email").value("test1@gmail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("hello"));
    }
}
