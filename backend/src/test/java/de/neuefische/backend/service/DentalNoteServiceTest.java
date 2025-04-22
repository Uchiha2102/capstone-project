package de.neuefische.backend.service;


import de.neuefische.backend.model.DentalNote;
import de.neuefische.backend.repository.DentalNoteRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DentalNoteServiceTest {

    private final DentalNoteRepository repository = mock(DentalNoteRepository.class);
    private final DentalNoteService service = new DentalNoteService(repository);


    @Test
    void getNotesByUser_shouldReturnListOfNotes() {
        //GIVEN
        String userId = "user123";
        DentalNote note1 = new DentalNote();
        note1.setId("note1");
        note1.setUserId(userId);

        DentalNote note2 = new DentalNote();
        note2.setId("note2");
        note2.setUserId(userId);

        when(repository.findByUserId(userId)).thenReturn(List.of(note1, note2));

        //WHEN
        List<DentalNote> notes = service.getNotesByUser(userId);

        //THEN
        assertThat(notes).containsExactly(note1, note2);
        verify(repository, times(1)).findByUserId(userId);

    }

    @Test
    void createNote_shouldSetUserIdAndSaveNote() {
        //GIVEN
        String userId = "user555";
        DentalNote note = new DentalNote();
        note.setTooth("tooth42");
        note.setNote("noteContent");

        DentalNote savedNote = new DentalNote();
        savedNote.setUserId(userId);
        savedNote.setId("note1");
        savedNote.setTooth("tooth42");
        savedNote.setNote("noteContent");

        when(repository.save(note)).thenReturn(savedNote);

        //When
        DentalNote result = service.createNote(userId, note);
        //Then
        assertThat(result).isEqualTo(savedNote);
        assertThat(note.getUserId()).isEqualTo(userId);
        verify(repository, times(1)).save(note);

    }

    @Test
    void updateNote_shouldUpdateExistingNote() {
        //GIVEN
        String id = "note1";
        String userId = "user123";

        DentalNote existingNote = new DentalNote();
        existingNote.setId(id);
        existingNote.setUserId(userId);
        existingNote.setTooth("oldTooth");
        existingNote.setNote("oldNote");

        DentalNote updatedNote = new DentalNote();
        updatedNote.setTooth("newTooth");
        updatedNote.setNote("newNote");

        when(repository.findById(id)).thenReturn(Optional.of(existingNote));
        when(repository.save(existingNote)).thenReturn(existingNote);

        //WHEN
        DentalNote result = service.updateNote(id, userId, updatedNote);

        //THEN
        assertThat(result.getTooth()).isEqualTo("newTooth");
        assertThat(result.getNote()).isEqualTo("newNote");
        verify(repository, times(1)).findById(id);
        verify(repository,times(1)).save(existingNote);

        }

        @Test
        void deleteNote_shouldDeleteExistingNote() {

            //GIVEN
            String id = "note1";
            String userId = "user123";

            DentalNote existingNote = new DentalNote();
            existingNote.setId(id);
            existingNote.setUserId(userId);

            when(repository.findById(id)).thenReturn(Optional.of(existingNote));
            doNothing().when(repository).deleteById(id);

            //WHEN
            service.deleteNoteById(id,userId);

            //THEN
            verify(repository,times(1)).findById(id);
            verify(repository,times(1)).deleteById(id);
    }
    }
