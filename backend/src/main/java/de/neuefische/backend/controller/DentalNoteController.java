package de.neuefische.backend.controller;

import de.neuefische.backend.dto.DentalNoteDTO;
import de.neuefische.backend.model.DentalNote;
import de.neuefische.backend.service.DentalNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dental-notes")
@RequiredArgsConstructor
public class DentalNoteController {

    private final DentalNoteService service;

    @GetMapping
    public List<DentalNote> getNotesByUser(@AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        return service.getNotesByUser(userId);
    }

    @PostMapping
    public DentalNote createNote(@RequestBody DentalNoteDTO noteDTO, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();

        DentalNote note = new DentalNote();
        note.setTooth(noteDTO.getTooth());
        note.setNote(noteDTO.getNote());

        return service.createNote(userId, note);
    }

    @PutMapping("/{id}")
    public DentalNote updateNote(@PathVariable String id, @RequestBody DentalNoteDTO noteDTO, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();

        DentalNote updatedNote = new DentalNote();
        updatedNote.setTooth(noteDTO.getTooth());
        updatedNote.setNote(noteDTO.getNote());

        return service.updateNote(id, userId, updatedNote);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id, @AuthenticationPrincipal OAuth2User user) {
        String userId = user.getAttributes().get("sub").toString();
        service.deleteNoteById(id, userId);
    }
}

