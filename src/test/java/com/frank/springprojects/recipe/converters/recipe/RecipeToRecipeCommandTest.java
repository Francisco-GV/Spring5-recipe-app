package com.frank.springprojects.recipe.converters.recipe;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.converters.category.CategoryToCategoryCommand;
import com.frank.springprojects.recipe.converters.ingredient.IngredientToIngredientCommand;
import com.frank.springprojects.recipe.converters.notes.NotesToNotesCommand;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureToUnitOfMeasureCommand;
import com.frank.springprojects.recipe.model.Category;
import com.frank.springprojects.recipe.model.Difficulty;
import com.frank.springprojects.recipe.model.Ingredient;
import com.frank.springprojects.recipe.model.Notes;
import com.frank.springprojects.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeToRecipeCommandTest {

    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
//        recipeToRecipeCommand = new RecipeToRecipeCommand(
//                new CategoryToCategoryCommand(),
//                new IngredientToIngredientCommand(
//                        new UnitOfMeasureToUnitOfMeasureCommand()),
//                new NotesToNotesCommand(recipeToRecipeCommand));
    }

    @Test
    public void testNullArgument() {
        assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    public void testNotNullArgument() {
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
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
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPreparationTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.getIngredients().add(ingredient);
        recipe.getIngredients().add(ingredient2);

        //when
        RecipeCommand command = recipeToRecipeCommand.convert(recipe);

        //then
        assertNotNull(command);
        assertEquals(RECIPE_ID, command.getId());
        assertEquals(COOK_TIME, command.getCookTime());
        assertEquals(PREP_TIME, command.getPreparationTime());
        assertEquals(DESCRIPTION, command.getDescription());
        assertEquals(DIFFICULTY, command.getDifficulty());
        assertEquals(DIRECTIONS, command.getDirections());
        assertEquals(SERVINGS, command.getServings());
        assertEquals(SOURCE, command.getSource());
        assertEquals(URL, command.getUrl());
        assertEquals(NOTES_ID, command.getNotes().getId());
        assertEquals(2, command.getCategories().size());
        assertEquals(2, command.getIngredients().size());
    }
}