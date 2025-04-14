package de.neuefische.backend.service;

import de.neuefische.backend.model.InvoiceDocument;
import de.neuefische.backend.repository.InvoiceDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceDocumentService{

    private final InvoiceDocumentRepository repository;

    public InvoiceDocument saveInvoice(String userId, MultipartFile file) throws IOException {
        InvoiceDocument document = new InvoiceDocument();
        document.setUserId(userId);
        document.setFileName(file.getOriginalFilename());
        document.setFileType(file.getContentType());
        document.setData(file.getBytes());
        document.setUploadDate(new Date());
        return repository.save(document);
    }

    public List<InvoiceDocument> getUserInvoices(String userId) {
        return repository.findByUserId(userId);
    }

    public InvoiceDocument getInvoiceById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteInvoice(String id) {
        repository.deleteById(id);
    }
}