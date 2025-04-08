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
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

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
        appointmentRepository.deleteAll();
    }

    private OAuth2User createMockOAuth2User(String userId) {
        return new DefaultOAuth2User(
                null,
                Map.of("sub", userId),
                "sub"
        );
    }

    @Test
    void shouldGetAppointmentsByUser() throws Exception {
        // GIVEN
        String userId = "mockUserId";
        Appointment appointment = new Appointment();
        appointment.setDate("2023-12-01");
        appointment.setTime("14:00");
        appointment.setUserId(userId);
        appointmentRepository.save(appointment);

        OAuth2User mockUser = createMockOAuth2User(userId);

        // WHEN & THEN
        mockMvc.perform(get("/api/appointments")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date", is("2023-12-01")))
                .andExpect(jsonPath("$[0].time", is("14:00")));
    }

    @Test
    void shouldCreateAppointment() throws Exception {
        // GIVEN
        String userId = "mockUserId";
        Appointment appointment = new Appointment();
        appointment.setDate("2024-12-07");
        appointment.setTime("16:00");
        appointment.setDentistName("Dr. Smith");
        appointment.setDescription("Dental check-up");

        OAuth2User mockUser = createMockOAuth2User(userId);

        // WHEN & THEN
        mockMvc.perform(post("/api/appointments")
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is("2024-12-07")))
                .andExpect(jsonPath("$.time", is("16:00")))
                .andExpect(jsonPath("$.dentistName", is("Dr. Smith")))
                .andExpect(jsonPath("$.description", is("Dental check-up")))
                .andExpect(jsonPath("$.userId", is(userId)));
    }

    @Test
    void shouldUpdateAppointment() throws Exception {
        // GIVEN
        String userId = "mockUserId";
        Appointment appointment = new Appointment();
        appointment.setDate("2024-12-01");
        appointment.setTime("12:00");
        appointment.setUserId(userId);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setDate("2024-12-10");
        updatedAppointment.setTime("13:30");
        updatedAppointment.setDentistName("Dr. Brown");
        updatedAppointment.setDescription("Updated dental check-up");

        OAuth2User mockUser = createMockOAuth2User(userId);

        // WHEN & THEN
        mockMvc.perform(put("/api/appointments/" + savedAppointment.getId())
                        .with(SecurityMockMvcRequestPostProcessors.oauth2Login().oauth2User(mockUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAppointment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date", is("2024-12-10")))
                .andExpect(jsonPath("$.time", is("13:30")))
                .andExpect(jsonPath("$.dentistName", is("Dr. Brown")))
                .andExpect(jsonPath("$.description", is("Updated dental check-up")));
    }

}
