package de.neuefische.backend.service;

import de.neuefische.backend.model.XRayImage;
import de.neuefische.backend.repository.XRayImageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Date;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class XrayServiceTest {

    private final XRayImageRepository repository = Mockito.mock(XRayImageRepository.class);
    private final XRayImageService service = new XRayImageService(repository);



    @Test
    void saveXRayImage_shouldSaveAndReturnImage() throws IOException {
        // Given
        String userId = "123";
        MockMultipartFile file = new MockMultipartFile("file", "example.png", "image/png", "test data".getBytes());

        XRayImage savedImage = new XRayImage();
        savedImage.setId("1");
        savedImage.setUserId(userId);
        savedImage.setFileName(file.getOriginalFilename());
        savedImage.setFileType(file.getContentType());
        savedImage.setData(file.getBytes());
        savedImage.setUploadDate(new Date());

        when(repository.save(any(XRayImage.class))).thenReturn(savedImage);

        // When
        XRayImage result = service.saveXRayImage(userId, file);

        // Then
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getFileName()).isEqualTo("example.png");
        assertThat(result.getFileType()).isEqualTo("image/png");
        assertThat(result.getData()).isEqualTo(file.getBytes());
    }

}
