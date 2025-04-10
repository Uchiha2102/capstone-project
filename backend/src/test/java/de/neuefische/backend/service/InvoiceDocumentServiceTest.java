package de.neuefische.backend.service;

import de.neuefische.backend.model.InvoiceDocument;
import de.neuefische.backend.repository.InvoiceDocumentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

class InvoiceDocumentServiceTest {

    private final InvoiceDocumentRepository repository = mock(InvoiceDocumentRepository.class);
    private final InvoiceDocumentService service = new InvoiceDocumentService(repository);

    @Test
    void saveInvoice_shouldSaveInvoice() throws IOException {

        String userId = "user1";
        MultipartFile mockFile = mock(MultipartFile.class);

        when(mockFile.getOriginalFilename()).thenReturn("invoice.pdf");
        when(mockFile.getContentType()).thenReturn("application/pdf");
        when(mockFile.getBytes()).thenReturn("Test Data".getBytes());

        InvoiceDocument savedDocument = new InvoiceDocument();
        savedDocument.setId("1");
        savedDocument.setUserId(userId);
        savedDocument.setFileName("invoice.pdf");
        savedDocument.setFileType("application/pdf");
        savedDocument.setData("Test Data".getBytes());
        savedDocument.setUploadDate(new Date());

        when(repository.save(any(InvoiceDocument.class))).thenReturn(savedDocument);

        InvoiceDocument result = service.saveInvoice(userId, mockFile);

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals("invoice.pdf", result.getFileName());
        assertEquals("application/pdf", result.getFileType());

        ArgumentCaptor<InvoiceDocument> captor = ArgumentCaptor.forClass(InvoiceDocument.class);
        verify(repository, times(1)).save(captor.capture());
        InvoiceDocument capturedDocument = captor.getValue();

        assertEquals(userId, capturedDocument.getUserId());
        assertEquals("invoice.pdf", capturedDocument.getFileName());
        assertEquals("application/pdf", capturedDocument.getFileType());
        assertArrayEquals("Test Data".getBytes(), capturedDocument.getData());
        assertNotNull(capturedDocument.getUploadDate());
    }

    @Test
    void getUserInvoices_shouldReturnInvoicesForUser() {
        String userId = "user1";
        InvoiceDocument document1 = new InvoiceDocument();
        document1.setId("1");
        document1.setUserId(userId);

        InvoiceDocument document2 = new InvoiceDocument();
        document2.setId("2");
        document2.setUserId(userId);

        List<InvoiceDocument> mockInvoices = Arrays.asList(document1, document2);
        when(repository.findByUserId(userId)).thenReturn(mockInvoices);

        List<InvoiceDocument> result = service.getUserInvoices(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findByUserId(userId);
    }


}

