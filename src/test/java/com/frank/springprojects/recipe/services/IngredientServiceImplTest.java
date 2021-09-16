package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.converters.ingredient.IngredientCommandToIngredient;
import com.frank.springprojects.recipe.converters.ingredient.IngredientToIngredientCommand;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureCommandToUnitOfMeasure;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureToUnitOfMeasureCommand;
import com.frank.springprojects.recipe.model.Ingredient;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import com.frank.springprojects.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    IngredientService ingredientService;

    @Before
    public void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
    }


    @Test
    public void findCommandByRecipeIdAndIngredientId() {
        // given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        for (int i = 0; i < 3; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId((long) i + 1);

            ingredient.setRecipe(recipe);
            recipe.getIngredients().add(ingredient);
        }

        // when
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeIdAndIngredientId(1L, 3L);

        // then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());

        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());

    }

    @Test
    public void saveIngredientCommand() {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        Recipe savedRecipe = new Recipe();
        savedRecipe.getIngredients().add(ingredientCommandToIngredient.convert(ingredientCommand));

        // when
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Recipe()));
        Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(savedRecipe);
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertEquals(Long.valueOf(3L), savedIngredientCommand.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(recipeRepository, Mockito.times(1)).save(Mockito.any(Recipe.class));
    }

    @Test
    public void deleteById() {
        // given
        long ingredientId = 3L;
        long recipeId = 1L;

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientId);
        ingredient.setRecipe(recipe);
        recipe.getIngredients().add(ingredient);

        // when
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));
        ingredientService.deleteById(recipeId, ingredientId);

        // then
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(recipeRepository, Mockito.times(1)).save(Mockito.any());
    }
}