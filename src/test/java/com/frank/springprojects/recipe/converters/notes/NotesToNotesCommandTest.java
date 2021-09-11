package com.frank.springprojects.recipe.converters.notes;

import com.frank.springprojects.recipe.commands.NotesCommand;
import com.frank.springprojects.recipe.model.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NotesToNotesCommandTest {

    NotesToNotesCommand notesToNotesCommand;

    @Before
    public void setUp() throws Exception {
        notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    public void testNullArgument() {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    public void testNotNullArgument() {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    public void convert() {
        final Long ID_VALUE = 1L;
        final String RECIPE_NOTES = "Notes";

        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);

        //when
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);

        //then
        assertNotNull(notesCommand);
        assertEquals(ID_VALUE, notesCommand.getId());
        assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
    }
}