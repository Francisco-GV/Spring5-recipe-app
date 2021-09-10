package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.converters.recipe.RecipeCommandToRecipe;
import com.frank.springprojects.recipe.converters.recipe.RecipeToRecipeCommand;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    private AutoCloseable mockitoCloseable;
    @Before
    public void setUp() {
        mockitoCloseable = MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void getRecipes() {
        Set<Recipe> recipesData = new HashSet<>(List.of(new Recipe()));

        Mockito.when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();
        Assert.assertEquals(1, recipes.size());

        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @After
    public void after() throws Exception {
        mockitoCloseable.close();
    }

}