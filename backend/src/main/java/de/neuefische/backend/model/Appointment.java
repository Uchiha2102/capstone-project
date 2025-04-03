package de.neuefische.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Appointment {
    @Id
    private String id;
    private String date;
    private String time;
    private String dentistName;
    private String description;
    private String userId;
}
