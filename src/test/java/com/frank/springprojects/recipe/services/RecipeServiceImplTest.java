package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.converters.recipe.RecipeCommandToRecipe;
import com.frank.springprojects.recipe.converters.recipe.RecipeToRecipeCommand;
import com.frank.springprojects.recipe.exceptions.NotFoundException;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    public void getRecipes() {
        Set<Recipe> recipesData = new HashSet<>(List.of(new Recipe()));

        Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();
        Assert.assertEquals(1, recipes.size());

        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void deleteById() {
        // given
        Long id = 1L;

        // when
        recipeService.deleteById(id);

        // then
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(id);
    }

    @Test(expected = NotFoundException.class)
    public void getRecipeByIdNotFound() {
        Mockito.when(recipeRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        recipeService.findById(1L);
    }
}