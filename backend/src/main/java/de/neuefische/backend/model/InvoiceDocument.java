package de.neuefische.backend.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class InvoiceDocument {
    @Id
    private String id;
    private String userId;
    private String fileName;
    private String fileType;
    private byte[] data;
    private Date uploadDate;

}
