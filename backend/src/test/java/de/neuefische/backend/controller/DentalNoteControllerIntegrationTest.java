package de.neuefische.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backend.dto.DentalNoteDTO;
import de.neuefische.backend.model.DentalNote;
import de.neuefische.backend.repository.DentalNoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DentalNoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DentalNoteRepository dentalNoteRepository;

    private final OAuth2User mockUser = new DefaultOAuth2User(
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            Map.of("sub", "user123"),
            "sub"
    );

    @AfterEach
    void tearDown() {
        dentalNoteRepository.deleteAll();
    }

    @Test
    void getNotesByUser_shouldReturnNotesForAuthenticatedUser() throws Exception {
        DentalNote note1 = new DentalNote();
        note1.setTooth("16");
        note1.setNote("Needs filling");
        note1.setUserId("user123");
        dentalNoteRepository.save(note1);

        DentalNote note2 = new DentalNote();
        note2.setTooth("11");
        note2.setNote("Regular check-up");
        note2.setUserId("user123");
        dentalNoteRepository.save(note2);

        mockMvc.perform(get("/api/dental-notes").with(oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tooth", is("16")))
                .andExpect(jsonPath("$[1].tooth", is("11")));
    }

    @Test
    void createNote_shouldSaveAndReturnCreatedNote() throws Exception {
        DentalNoteDTO noteDTO = new DentalNoteDTO();
        noteDTO.setTooth("Canine");
        noteDTO.setNote("Requires cleaning");

        mockMvc.perform(post("/api/dental-notes")
                        .with(oauth2Login().oauth2User(mockUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tooth", is("Canine")))
                .andExpect(jsonPath("$.note", is("Requires cleaning")))
                .andExpect(jsonPath("$.userId", is("user123")));
    }

    @Test
    void updateNote_shouldUpdateAndReturnUpdatedNote() throws Exception {
        DentalNote existingNote = new DentalNote();
        existingNote.setTooth("14");
        existingNote.setNote("Check sensitivity");
        existingNote.setUserId("user123");
        dentalNoteRepository.save(existingNote);

        DentalNoteDTO updatedNoteDTO = new DentalNoteDTO();
        updatedNoteDTO.setTooth("14");
        updatedNoteDTO.setNote("X-ray needed");

        mockMvc.perform(put("/api/dental-notes/" + existingNote.getId())
                        .with(oauth2Login().oauth2User(mockUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedNoteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tooth", is("14")))
                .andExpect(jsonPath("$.note", is("X-ray needed")))
                .andExpect(jsonPath("$.userId", is("user123")));
    }

    @Test
    void deleteNote_shouldDeleteNoteForAuthenticatedUser() throws Exception {
        DentalNote existingNote = new DentalNote();
        existingNote.setTooth("18");
        existingNote.setNote("Extraction planned");
        existingNote.setUserId("user123");
        dentalNoteRepository.save(existingNote);

        mockMvc.perform(delete("/api/dental-notes/" + existingNote.getId())
                        .with(oauth2Login().oauth2User(mockUser)))
                .andExpect(status().isOk());

        List<DentalNote> remainingNotes = dentalNoteRepository.findByUserId("user123");
        assert remainingNotes.isEmpty();
    }
}
