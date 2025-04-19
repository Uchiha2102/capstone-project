package de.neuefische.backend.service;


import de.neuefische.backend.model.DentalNote;
import de.neuefische.backend.repository.DentalNoteRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        when(repository.findByUserId(userId)).thenReturn(List.of(note1,note2));

        //WHEN
        List<DentalNote> notes = service.getNotesByUser(userId);

        //THEN
        assertThat(notes).containsExactly(note1,note2);
        verify(repository, times(1)).findByUserId(userId);





    }
}
