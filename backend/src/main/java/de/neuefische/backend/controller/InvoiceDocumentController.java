package de.neuefische.backend.controller;

import de.neuefische.backend.model.InvoiceDocument;
import de.neuefische.backend.service.InvoiceDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class InvoiceDocumentController {

    private final InvoiceDocumentService service;

    @GetMapping
    public ResponseEntity<List<InvoiceDocument>> getUserInvoices(@AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        return ResponseEntity.ok(service.getUserInvoices(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDocument> getInvoiceById(@PathVariable String id,
                                                          @AuthenticationPrincipal OAuth2User user) {
        InvoiceDocument invoice = service.getInvoiceById(id);
        String userId = user.getAttributes().get("sub").toString();

        if (invoice == null || !invoice.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<Object> uploadInvoice(@RequestParam("file") MultipartFile file,
                                                @AuthenticationPrincipal OAuth2User user) {
        try {
            String userId = user.getAttributes().get("sub").toString();
            InvoiceDocument savedInvoice = service.saveInvoice(userId, file);
            return ResponseEntity.ok(savedInvoice);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error uploading file");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable String id,
                                                @AuthenticationPrincipal OAuth2User user) {
        InvoiceDocument invoice = service.getInvoiceById(id);
        String userId = user.getAttributes().get("sub").toString();

        if (invoice == null || !invoice.getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("Access denied: This invoice does not belong to you");
        }

        service.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted successfully");
    }
}