package de.neuefische.backend.dto;

import lombok.Data;
@Data
public class AppointmentDTO {
    private String date;
    private String time;
    private String dentistName;
    private String description;
}
