package com.frank.springprojects.recipe.converters.recipe;

import com.frank.springprojects.recipe.commands.CategoryCommand;
import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.commands.NotesCommand;
import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.converters.category.CategoryCommandToCategory;
import com.frank.springprojects.recipe.converters.ingredient.IngredientCommandToIngredient;
import com.frank.springprojects.recipe.converters.notes.NotesCommandToNotes;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureCommandToUnitOfMeasure;
import com.frank.springprojects.recipe.model.Difficulty;
import com.frank.springprojects.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeCommandToRecipeTest {

    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        recipeCommandToRecipe = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(
                        new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullArgument() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    public void testNotNullArgument() {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
        final Long RECIPE_ID = 1L;
        final Integer COOK_TIME = 5;
        final Integer PREP_TIME = 7;
        final String DESCRIPTION = "My Recipe";
        final String DIRECTIONS = "Directions";
        final Difficulty DIFFICULTY = Difficulty.EASY;
        final Integer SERVINGS = 3;
        final String SOURCE = "Source";
        final String URL = "Some URL";
        final Long CAT_ID_1 = 1L;
        final Long CAT_ID2 = 2L;
        final Long INGRED_ID_1 = 3L;
        final Long INGRED_ID_2 = 4L;
        final Long NOTES_ID = 9L;

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPreparationTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        recipeCommand.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        recipeCommand.getCategories().add(category);
        recipeCommand.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        recipeCommand.getIngredients().add(ingredient);
        recipeCommand.getIngredients().add(ingredient2);

        //when
        Recipe recipe  = recipeCommandToRecipe.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPreparationTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }
}