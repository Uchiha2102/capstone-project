package de.neuefische.backend.controller;

import de.neuefische.backend.service.XRayImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

public class XrayImageControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private XRayImageService service;

    @Test
    void uploadImage_shouldReturnOkIfImageIsSaved() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "tes-image.png",
                MediaType.IMAGE_PNG_VALUE,
                "Test image content".getBytes()
        )
    }

}
