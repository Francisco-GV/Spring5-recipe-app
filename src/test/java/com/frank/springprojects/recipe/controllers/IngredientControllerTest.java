package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.services.IngredientService;
import com.frank.springprojects.recipe.services.RecipeService;
import com.frank.springprojects.recipe.services.UnitOfMeasureService;
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

import java.util.HashSet;

@RunWith(MockitoJUnitRunner.class)
public class IngredientControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredients() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();

        // when
        Mockito.when(recipeService.findCommandById(Mockito.any())).thenReturn(recipeCommand);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.anyLong());
    }

    @Test
    public void showIngredient() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();

        // when
        Mockito.when(ingredientService.findCommandByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(ingredientCommand);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/2/show"));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));
    }

    @Test
    public void updateRecipeingredient() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();

        // when
        Mockito.when(ingredientService.findCommandByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(ingredientCommand);
        Mockito.when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/2/update"));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("unitOfMeasureList"));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecipeId(2L);

        // when
        Mockito.when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(ingredientCommand);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "3")
                .param("description", "Some string"));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    public void newIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findCommandByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);
        Mockito.when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/2/update"));

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("unitOfMeasureList"));
    }
}