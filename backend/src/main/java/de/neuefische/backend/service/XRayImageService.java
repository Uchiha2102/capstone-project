package de.neuefische.backend.service;

import de.neuefische.backend.model.XRayImage;
import de.neuefische.backend.repository.XRayImageRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service

public class XRayImageService {
    private final XRayImageRepository repository;

    public XRayImageService(XRayImageRepository repository) {
        this.repository = repository;
    }

    public XRayImage saveXRayImage(String userId, MultipartFile file) throws IOException {
        XRayImage image = new XRayImage();
        image.setUserId(userId);
        image.setFileName(file.getOriginalFilename());
        image.setFileType(file.getContentType());
        image.setData(file.getBytes());
        image.setUploadDate(new Date());
        return repository.save(image);
    }

    public List<XRayImage> getUserImages(String userId) {
        return repository.findByUserId(userId);
    }

    public XRayImage getImageById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteImage(String id) {
        repository.deleteById(id);
    }
}
