package de.neuefische.backend.service;

import de.neuefische.backend.model.Appointment;
import de.neuefische.backend.repository.AppointmentRepository;

import java.util.List;

public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }


    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Appointment createAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public void deleteAppointment(String id){
        repository.deleteById(id);
    }
}
