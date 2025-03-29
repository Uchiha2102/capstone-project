package de.neuefische.backend.service;

import de.neuefische.backend.model.XrayImage;
import de.neuefische.backend.repository.XRayImageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class XrayServiceTest {

    private final XRayImageRepository repository = Mockito.mock(XRayImageRepository.class);
    private final XRayImageService service = new XRayImageService(repository);

    @Test
    void saveXRayImage_shouldSaveAndReturnImage() throws IOException {
        // GIVEN
        String userId = "123";
        MockMultipartFile file = new MockMultipartFile("file", "example.png", "image/png", "test data".getBytes());

        XrayImage savedImage = new XrayImage();
        savedImage.setId("1");
        savedImage.setUserId(userId);
        savedImage.setFileName(file.getOriginalFilename());
        savedImage.setFileType(file.getContentType());
        savedImage.setData(file.getBytes());
        savedImage.setUploadDate(new Date());

        when(repository.save(any(XrayImage.class))).thenReturn(savedImage);

        // WHEN
        XrayImage result = service.saveXRayImage(userId, file);

        // THEN
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getFileName()).isEqualTo("example.png");
        assertThat(result.getFileType()).isEqualTo("image/png");
        assertThat(result.getData()).isEqualTo(file.getBytes());
    }

    @Test
    void getUserImages_shouldReturnListOfImages() {

        //GIVEN
        String userId = "123";
        XrayImage image1 = new XrayImage();
        image1.setId("1");
        XrayImage image2 = new XrayImage();
        image2.setId("2");

        when(repository.findByUserId(userId)).thenReturn(Arrays.asList(image1, image2));

        // WHEN
        var result = service.getUserImages(userId);

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(1).getId()).isEqualTo("2");
    }

    @Test
    void getImageById_shouldReturnImage() {
        // GIVEN
        String id = "1";
        XrayImage image = new XrayImage();
        image.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(image));

        // WHEN
        var result = service.getImageById(id);

        //THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void deleteImage_shouldDeleteImageById() {
        //GIVEN
        String id = "1";

        //WHEN
        service.deleteImage(id);

        //THEN
        verify(repository,times(1)).deleteById(id);
    }
}