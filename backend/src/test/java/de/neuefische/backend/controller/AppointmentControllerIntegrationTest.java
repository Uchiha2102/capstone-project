package de.neuefische.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.model.Appointment;
import de.neuefische.backend.repository.AppointmentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        appointmentRepository.deleteAll(); // Datenbank wird nach jedem Test geleert.
    }

    @Test
    void shouldGetAllAppointments() throws Exception {
        //GIVEN
        Appointment appointment = new Appointment();
        appointment.setDate("2023-12-01");
        appointment.setTime("14:00");
        appointmentRepository.save(appointment);

        // WHEN & THEN
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is("2023-12-01")))
                .andExpect(jsonPath("$[0].time", is("14:00")));

    }

    @Test
    void shouldCreateAppointment() throws Exception {
        //GIVEN
        Appointment appointment = new Appointment();
        appointment.setDate("2024-12-07");
        appointment.setTime("16:00");

        //WHEN + THEN
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is("2024-12-07")))
                .andExpect(jsonPath("$.time", is("16:00")));
    }

    @Test
    void shouldUpdateAppointment() throws Exception {
        //GIVEN
        Appointment appointment = new Appointment();
        appointment.setDate("2024-12-01");
        appointment.setTime("12:00");
        Appointment savedAppointment = appointmentRepository.save(appointment);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setDate("2024-12-10");
        updatedAppointment.setTime("13:30");

        //WHEN + THEN

        mockMvc.perform(put("/api/appointments/" + savedAppointment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAppointment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is("2024-12-10")))
                .andExpect(jsonPath("$.time", is("13:30")));
    }

    @Test
    void shouldDeleteAppointment() throws Exception {

        //GIVEN
        Appointment appointment = new Appointment();
        appointment.setDate("2025-02-21");
        appointment.setTime("14:00");
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // WHEN + THEN

        mockMvc.perform(delete("/api/appointments/" + savedAppointment.getId()))
                .andExpect(status().isOk());
    }
}
