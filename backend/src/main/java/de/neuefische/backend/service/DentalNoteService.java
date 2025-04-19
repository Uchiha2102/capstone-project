package de.neuefische.backend.service;

import de.neuefische.backend.model.DentalNote;
import de.neuefische.backend.repository.DentalNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DentalNoteService {

    private final DentalNoteRepository repository;

    public List<DentalNote> getNotesByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public DentalNote createNote(String userId, DentalNote note) {
        note.setUserId(userId);
        return repository.save(note);
    }

    public DentalNote updateNote(String id, String userId, DentalNote updatedNote) {
        DentalNote existingNote = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note with ID not found"));

        if (!existingNote.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to update this note.");
        }

        existingNote.setTooth(updatedNote.getTooth());
        existingNote.setNote(updatedNote.getNote());
        return repository.save(existingNote);
    }

    public void deleteNoteById(String id, String userId) {
        DentalNote note = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note with ID not found"));

        if (!note.getUserId().equals(userId)) {
            throw new IllegalArgumentException("You are not authorized to delete this note.");
        }

        repository.deleteById(id);
    }
}

