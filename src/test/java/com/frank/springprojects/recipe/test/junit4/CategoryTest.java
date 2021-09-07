package com.frank.springprojects.recipe.test.junit4;

import com.frank.springprojects.recipe.model.Category;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
    private Category category;

    @Before
    public void setup() {
        category = new Category();
        category.setId(10L);
        category.setDescription("Test description");
        category.setRecipes(null);
    }
    @Test
    public void getId() {
        assertEquals(Long.valueOf(10L), category.getId());
    }

    @Test
    public void getDescription() {
        assertEquals("Test description", category.getDescription());
    }

    @Test
    public void getRecipes() {
        assertNull(category.getRecipes());
    }
}