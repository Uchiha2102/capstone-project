package de.neuefische.backend.service;

import de.neuefische.backend.model.Appointment;
import de.neuefische.backend.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private final AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
    private final AppointmentService appointmentService = new AppointmentService(appointmentRepository);


    @Test
    void shouldGetALlAppointments() {
        // GIVEN
        Appointment appointment1 = new Appointment();
        appointment1.setId("1");
        Appointment appointment2 = new Appointment();
        appointment2.setId("2");

        when(appointmentRepository.findAll()).thenReturn(List.of(appointment1, appointment2));

        //When
        List<Appointment> appointments = appointmentService.getAllAppointments();

        //THEN
        assertEquals(2, appointments.size());
        verify(appointmentRepository).findAll();
    }

    @Test
    void shouldCreateAppointment() {

        //GIVEN
        Appointment appointment = new Appointment();
        appointment.setDate("2024-12-03");

        when(appointmentRepository.save(appointment)).thenReturn(appointment);

        //WHEN
        Appointment result = appointmentService.createAppointment(appointment);

        //THEN
        assertEquals("2024-12-03", result.getDate());
        verify(appointmentRepository).save(appointment);

    }

    @Test
    void shouldUpdateAppointment() {
        // GIVEN
        Appointment existingAppointment = new Appointment();
        existingAppointment.setId("1");
        existingAppointment.setDate("2023-12-01");

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setDate("2024-12-10");
        updatedAppointment.setTime("13:00");

        when(appointmentRepository.findById("1")).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(existingAppointment)).thenReturn(existingAppointment);

        // WHEN
        Appointment result = appointmentService.updateAppointment("1", updatedAppointment);

        // THEN
        assertEquals("2024-12-10", result.getDate());
        assertEquals("13:00", result.getTime());

        verify(appointmentRepository).findById("1");
        verify(appointmentRepository).save(existingAppointment);

    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingAppointment() {

        //GIVEN
        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setDate("2023-12-10");

        when(appointmentRepository.findById("999")).thenReturn(Optional.empty());

        //THEN
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                appointmentService.updateAppointment("999", updatedAppointment));

        assertEquals("Appointment with ID 999 not found", exception.getMessage());
        verify(appointmentRepository).findById("999");
        verify(appointmentRepository, never()).save(any());

    }

    @Test
    void shouldDeleteAppointment() {

        //GIVEN
        String id = "1";

        doNothing().when(appointmentRepository).deleteById(id);

        //WHEN
        appointmentService.deleteAppointment(id);

        //THEN
        verify(appointmentRepository).deleteById(id);

    }

    @Test
    void shouldGetAppointmentsByUserId() {
        // GIVEN
        String userId = "123";
        Appointment appointment1 = new Appointment();
        appointment1.setId("1");
        appointment1.setUserId(userId);

        Appointment appointment2 = new Appointment();
        appointment2.setId("2");
        appointment2.setUserId(userId);

        when(appointmentRepository.findByUserId(userId)).thenReturn(List.of(appointment1, appointment2));

        // WHEN
        List<Appointment> result = appointmentService.getAppointmentsByUserId(userId);

        // THEN
        assertEquals(2, result.size());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(userId, result.get(1).getUserId());
        verify(appointmentRepository).findByUserId(userId);
    }

    @Test
    void shouldGetPastAppointmentByUserId() {
        //GIVEN
        String userId = "123";

        Appointment pastAppointment = new Appointment();
        pastAppointment.setId("1");
        pastAppointment.setDate(LocalDate.now().minusDays(5).toString()); // vergangenes Datum

        Appointment futureAppointment = new Appointment();
        futureAppointment.setId("2");
        futureAppointment.setUserId(userId);
        futureAppointment.setDate(LocalDate.now().plusDays(5).toString()); //zukünftiges Datum

        when(appointmentRepository.findByUserId(userId)).thenReturn(List.of(pastAppointment,futureAppointment));

        //WHEN
        List<Appointment> result = appointmentService.getPastAppointmentsByUserId(userId);

        //THEN
        assertEquals(1,result.size());
        assertEquals(pastAppointment.getId(), result.get(0).getId());
        verify(appointmentRepository).findByUserId(userId);
    }
}
