package de.neuefische.backend.controller;

import de.neuefische.backend.model.Appointment;
import de.neuefische.backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public List<Appointment> getAppointmentsByUser(@AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        return service.getAppointmentsByUserId(userId);
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
       String userId = user.getAttributes().get("sub").toString();
       Appointment appointment = service.getAppointmentById(id);

       if (!appointment.getUserId().equals(userId)) {
           throw new IllegalArgumentException("You don't have access to this appointment!");
       }
        return appointment;
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        appointment.setUserId(userId);
        return service.createAppointment(appointment);

    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable String id, @RequestBody Appointment appointment, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        Appointment existingAppointment = service.getAppointmentById(id);

        if (!existingAppointment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You don't have access to update this appointment!");
        }

        appointment.setUserId(userId);
        return service.updateAppointment(id, appointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        Appointment appointment = service.getAppointmentById(id);

        if (!appointment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Access denied: You cannot delete this appointment!");
        }

        service.deleteAppointment(id);
    }
    }
