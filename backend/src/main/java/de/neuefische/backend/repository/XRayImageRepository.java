package de.neuefische.backend.repository;

import de.neuefische.backend.model.XrayImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XRayImageRepository extends MongoRepository<XrayImage, String> {
    List<XrayImage> findByUserId(String userId);
}

