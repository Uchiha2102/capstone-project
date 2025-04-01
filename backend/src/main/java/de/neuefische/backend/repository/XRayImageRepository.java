package de.neuefische.backend.repository;

import de.neuefische.backend.model.XRayImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XRayImageRepository extends MongoRepository<XRayImage, String> {
    List<XRayImage> findByUserId(String userId);
}

