package de.neuefische.backend.controller;


import de.neuefische.backend.model.Appointment;
import de.neuefische.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService service;

    @GetMapping
    public List<Appointment> getAllAppointments(){
        return service.getAllAppointments();
    }
    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return service.createAppointment(appointment);
    }




}
