package org.cs3219.project.peerprep.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AuthenticationController.class)
@Disabled
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled
    void register() {

    }

    @Test
    @Disabled
    void activate() {
    }

    @Test
    @Disabled
    void preResetPassword() {
    }

    @Test
    @Disabled
    void resetPassword() {
    }
}