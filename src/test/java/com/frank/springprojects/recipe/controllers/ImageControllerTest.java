package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.services.ImageService;
import com.frank.springprojects.recipe.services.RecipeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ErrorHandlerController())
                .build();
    }

    @Test
    public void showUploadForm() throws Exception {
        // given
        long id = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(id);

        // when
        Mockito.when(recipeService.findCommandById(id)).thenReturn(recipeCommand);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"));

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(id);
    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "handleImagePostTest".getBytes());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .multipart("/recipe/1/image").file(multipartFile));

        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/show/1"));

        Mockito.verify(imageService, Mockito.times(1)).saveImageFile(Mockito.anyLong(), Mockito.any());
    }


    @Test
    public void renderImageFromDB() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s = "fake image text";
        recipeCommand.setImage(s.getBytes());

        //when
        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(recipeCommand);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/recipe_image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        byte[] reponseBytes = response.getContentAsByteArray();

        // then
        Assert.assertEquals(s.getBytes().length, reponseBytes.length);
    }

    @Test
    public void testGetImageNumberFormatException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/one/recipe_image"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("error"));
    }
}