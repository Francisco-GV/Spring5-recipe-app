package com.frank.springprojects.recipe.test.junit4;

import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import com.frank.springprojects.recipe.services.RecipeService;
import com.frank.springprojects.recipe.services.RecipeServiceImpl;
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

    private AutoCloseable mockitoCloseable;
    @Before
    public void setUp() {
        mockitoCloseable = MockitoAnnotations.openMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
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