package de.neuefische.backend.service;

import de.neuefische.backend.model.Appointment;
import de.neuefische.backend.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repository;

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Appointment createAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public Appointment updateAppointment(String id, Appointment updatedAppointment) {
        Appointment existingAppointment = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment with ID " + id + " not found"));
        existingAppointment.setDate(updatedAppointment.getDate());
        existingAppointment.setTime(updatedAppointment.getTime());
        existingAppointment.setDentistName(updatedAppointment.getDentistName());
        existingAppointment.setDescription(updatedAppointment.getDescription());
        return repository.save(existingAppointment);
    }

    public void deleteAppointment(String id) {
        repository.deleteById(id);
    }
}
