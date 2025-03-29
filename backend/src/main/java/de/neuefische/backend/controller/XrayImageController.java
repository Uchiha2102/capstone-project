package de.neuefische.backend.controller;

import de.neuefische.backend.model.XRayImage;
import de.neuefische.backend.service.XRayImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/xray")
public class XrayImageController {
    private final XRayImageService service;

    public XrayImageController(XRayImageService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadImage(@RequestParam("userId") String userId,
                                         @RequestParam("file") MultipartFile file) {
        try {
            XRayImage savedImage = service.saveXRayImage(userId, file);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<XRayImage>> getUserImages(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserImages(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<XRayImage> getImageById(@PathVariable String id) {
        XRayImage image = service.getImageById(id);
        return image != null ? ResponseEntity.ok(image) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable String id) {
        service.deleteImage(id);
        return ResponseEntity.ok("Image deleted");
    }
}
