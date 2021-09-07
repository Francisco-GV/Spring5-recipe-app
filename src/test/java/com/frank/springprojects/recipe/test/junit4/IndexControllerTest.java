package com.frank.springprojects.recipe.test.junit4;

import com.frank.springprojects.recipe.controllers.IndexController;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;

public class IndexControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    @InjectMocks
    private IndexController indexController;

    @Captor
    private ArgumentCaptor<Set<Recipe>> captor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void getIndexPage() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        Set<Recipe> recipes = new HashSet<>(List.of(recipe1, recipe2));

        Mockito.when(recipeService.getRecipes()).thenReturn(recipes);
        String view = indexController.getIndexPage(model);

        Assert.assertEquals("index", view);

        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(eq("recipes"), captor.capture());

        Assert.assertEquals(recipes, captor.getValue());
    }
}