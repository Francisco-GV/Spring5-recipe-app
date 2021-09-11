package com.frank.springprojects.recipe.converters.notes;

import com.frank.springprojects.recipe.commands.NotesCommand;
import com.frank.springprojects.recipe.converters.category.CategoryCommandToCategory;
import com.frank.springprojects.recipe.converters.ingredient.IngredientCommandToIngredient;
import com.frank.springprojects.recipe.converters.recipe.RecipeCommandToRecipe;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureCommandToUnitOfMeasure;
import com.frank.springprojects.recipe.model.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class NotesCommandToNotesTest {

    NotesCommandToNotes notesCommandToNotes;

    @Before
    public void setUp() throws Exception {
        notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    public void testNullArgument() {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    public void testNotNullArgument() {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }

    @Test
    public void convert() {
        final Long ID_VALUE = 1L;
        final String RECIPE_NOTES = "Notes";

        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(ID_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = notesCommandToNotes.convert(notesCommand);

        //then
        assertNotNull(notes);
        assertEquals(ID_VALUE, notes.getId());
        assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
    }
}