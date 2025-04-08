package de.neuefische.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getMe_shouldReturnSubClaim() throws Exception {
        OAuth2User mockUser = new DefaultOAuth2User(
                null,
                Map.of("sub", "mock-sub-id"),
                "sub");


        mockMvc.perform(get("/api/auth/me")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login()
                                .oauth2User(mockUser)))
                .andExpect(status().isOk())
                .andExpect(content().string("mock-sub-id"));
    }

}
