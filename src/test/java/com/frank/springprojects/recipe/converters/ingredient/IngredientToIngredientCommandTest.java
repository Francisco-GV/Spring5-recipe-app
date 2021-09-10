package com.frank.springprojects.recipe.converters.ingredient;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureToUnitOfMeasureCommand;
import com.frank.springprojects.recipe.model.Ingredient;
import com.frank.springprojects.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientToIngredientCommandTest {

    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Before
    public void setUp() throws Exception {
        ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullArgument() {
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    public void testNotNullArgument() {
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }

    @Test
    public void convert() {
        final Recipe RECIPE = new Recipe();
        final BigDecimal AMOUNT = new BigDecimal(1);
        final String DESCRIPTION = "Cheeseburger";
        final Long ID_VALUE = 1L;
        final Long UOM_ID = 2L;

        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setRecipe(RECIPE);
        ingredient.setAmount(AMOUNT);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setUnitOfMeasure(null);

        //when
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertEquals(ID_VALUE, ingredientCommand.getId());
        assertEquals(AMOUNT, ingredientCommand.getAmount());
        assertEquals(DESCRIPTION, ingredientCommand.getDescription());
        assertNull(ingredientCommand.getUnitOfMeasure());
    }
}