package de.neuefische.backend.controller;

import de.neuefische.backend.dto.AppointmentDTO;
import de.neuefische.backend.exceptions.AccessDeniedException;
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
           throw new AccessDeniedException("You don't have access to this appointment!");
       }
        return appointment;
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentDTO appointmentDTO, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();

        Appointment appointment = new Appointment();
        appointment.setDate(appointmentDTO.getDate());
        appointment.setTime(appointmentDTO.getTime());
        appointment.setDentistName(appointmentDTO.getDentistName());
        appointment.setDescription(appointmentDTO.getDescription());
        appointment.setUserId(userId);

        return service.createAppointment(appointment);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable String id, @RequestBody AppointmentDTO appointmentDTO, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        Appointment existingAppointment = service.getAppointmentById(id);

        if (!existingAppointment.getUserId().equals(userId)) {
            throw new AccessDeniedException("You don't have access to update this appointment!");
        }
        existingAppointment.setDate(appointmentDTO.getDate());
        existingAppointment.setTime(appointmentDTO.getTime());
        existingAppointment.setDentistName(appointmentDTO.getDentistName());
        existingAppointment.setDescription(appointmentDTO.getDescription());
        existingAppointment.setUserId(userId);
        return service.updateAppointment(id, existingAppointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        Appointment appointment = service.getAppointmentById(id);

        if (!appointment.getUserId().equals(userId)) {
            throw new AccessDeniedException("Access denied: You cannot delete this appointment!");
        }

        service.deleteAppointment(id);
    }

    @GetMapping("/history")
    public List<Appointment> getPastAppointments(@AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        return service.getPastAppointmentsByUserId(userId);
    }
}