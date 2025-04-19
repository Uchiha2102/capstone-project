package de.neuefische.backend.repository;

import de.neuefische.backend.model.DentalNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentalNoteRepository extends MongoRepository<DentalNote, String> {
    List<DentalNote> findByUserId(String userId);
}
