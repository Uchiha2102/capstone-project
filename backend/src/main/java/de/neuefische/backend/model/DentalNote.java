package de.neuefische.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class DentalNote {
    @Id
    private String id;
    private String tooth;
    private String note;
    private String userId;
}
