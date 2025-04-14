package de.neuefische.backend.controller;

import de.neuefische.backend.model.XRayImage;
import de.neuefische.backend.repository.XRayImageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class XRayImageControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private XRayImageRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void uploadImage_shouldSaveImageAndReturnIt() throws Exception {
        // GIVEN
        MockMultipartFile file = new MockMultipartFile("file", "xray-image.png", "image/png", "dummy-image-data".getBytes());

        OAuth2User mockUser = new DefaultOAuth2User(
                null,
                Map.of("sub", "mockUserId"),
                "sub"
        );

        // WHEN & THEN
        mockMvc.perform(multipart("/api/xray")
                        .file(file)
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("xray-image.png"))
                .andExpect(jsonPath("$.userId").value("mockUserId"));

        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    void getUserImages_shouldReturnImagesOfUser() throws Exception {
        // GIVEN
        XRayImage image = new XRayImage();
        image.setUserId("mockUserId");
        image.setFileName("xray-image.png");
        image.setFileType("image/png");
        image.setData("dummy-image-data".getBytes());
        image.setUploadDate(new Date());
        repository.save(image);

        OAuth2User mockUser = new DefaultOAuth2User(
                null,
                Map.of("sub", "mockUserId"),
                "sub"
        );

        // WHEN & THEN
        mockMvc.perform(get("/api/xray")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fileName").value("xray-image.png"));
    }

    @Test
    void getImageById_shouldReturnImageIfUserAuthorized() throws Exception {
        // GIVEN
        XRayImage image = new XRayImage();
        image.setUserId("mockUserId");
        image.setFileName("xray-image.png");
        image.setFileType("image/png");
        image.setData("dummy-image-data".getBytes());
        image.setUploadDate(new Date());
        repository.save(image);

        OAuth2User mockUser = new DefaultOAuth2User(
                null,
                Map.of("sub", "mockUserId"),
                "sub"
        );

        // WHEN & THEN
        mockMvc.perform(get("/api/xray/" + image.getId())
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value("xray-image.png"));
    }

    @Test
    void getImageById_shouldReturnForbiddenIfUserUnauthorized() throws Exception {
        // GIVEN
        XRayImage image = new XRayImage();
        image.setUserId("unauthorizedUserId");
        image.setFileName("unauthorized-image.png");
        image.setFileType("image/png");
        image.setData("dummy-image-data".getBytes());
        image.setUploadDate(new Date());
        repository.save(image);

        OAuth2User mockUser = new DefaultOAuth2User(
                null,
                Map.of("sub", "mockUserId"),
                "sub"
        );

        // WHEN & THEN
        mockMvc.perform(get("/api/xray/" + image.getId())
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteImage_shouldDeleteImageIfUserAuthorized() throws Exception {
        // GIVEN
        XRayImage image = new XRayImage();
        image.setUserId("mockUserId");
        image.setFileName("xray-image.png");
        image.setFileType("image/png");
        image.setData("dummy-image-data".getBytes());
        image.setUploadDate(new Date());
        repository.save(image);

        OAuth2User mockUser = new DefaultOAuth2User(
                null,
                Map.of("sub", "mockUserId"),
                "sub"
        );

        // WHEN & THEN
        mockMvc.perform(delete("/api/xray/" + image.getId())
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk());

        assertThat(repository.findAll()).isEmpty();
    }

}

