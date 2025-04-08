package de.neuefische.backend.repository;

import de.neuefische.backend.model.InvoiceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceDocumentRepository extends MongoRepository<InvoiceDocument,String> {
    List<InvoiceDocument> findByUserId(String userId);
}
