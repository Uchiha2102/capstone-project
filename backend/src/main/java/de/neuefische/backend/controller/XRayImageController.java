package de.neuefische.backend.controller;

import de.neuefische.backend.model.XRayImage;
import de.neuefische.backend.service.XRayImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/xray")
@RequiredArgsConstructor
public class XRayImageController {
    private final XRayImageService service;

    @PostMapping
    public ResponseEntity<Object> uploadImage(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal OAuth2User user) {
        try {
            String userId = user.getAttributes().get("sub").toString();
            XRayImage savedImage = service.saveXRayImage(userId, file);
            return ResponseEntity.ok(savedImage);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file");
        }
    }

    @GetMapping
    public ResponseEntity<List<XRayImage>> getUserImages(@AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        return ResponseEntity.ok(service.getUserImages(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<XRayImage> getImageById(@PathVariable String id,
                                                  @AuthenticationPrincipal OAuth2User user) {
        XRayImage image = service.getImageById(id);
        String userId = user.getAttributes().get("sub").toString();

        if (image == null || !image.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable String id,
                                              @AuthenticationPrincipal OAuth2User user) {
        XRayImage image = service.getImageById(id);
        String userId = user.getAttributes().get("sub").toString();

        if (image == null || !image.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("Access denied: This image does not belong to you");
        }

        service.deleteImage(id);
        return ResponseEntity.ok("Image deleted successfully");
    }
}