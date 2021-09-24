package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.exceptions.NotFoundException;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandlerController())
                .build();
    }

    @Test
    public void showById() throws Exception {
        long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        Mockito.when(recipeService.findById(id)).thenReturn(recipe);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void newRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void updateRecipe() throws Exception {
        long id = 2L;
        RecipeCommand command = new RecipeCommand();
        command.setId(id);

        Mockito.when(recipeService.findCommandById(2L)).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/update/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(id);
    }

    @Test
    public void saveOrUpdate() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any()))
                .thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "2")
                        .param("description", "Some description")
                        .param("directions", "Some directions"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/show/2"));
    }

    @Test
    public void saveOrUpdateqValidationFails() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/form"));
    }

    @Test
    public void testGetRecipeNotFoundException() throws Exception {
        Mockito.when(recipeService.findById(Mockito.anyLong()))
                .thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }

    @Test
    public void testNumberFormatException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/one"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }
}