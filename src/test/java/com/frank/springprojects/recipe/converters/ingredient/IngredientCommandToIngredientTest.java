package com.frank.springprojects.recipe.converters.ingredient;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.commands.UnitOfMeasureCommand;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureCommandToUnitOfMeasure;
import com.frank.springprojects.recipe.model.Ingredient;
import com.frank.springprojects.recipe.model.Recipe;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IngredientCommandToIngredientTest {

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {
        ingredientCommandToIngredient =
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullArgument() {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    public void testNotNullArgument() {
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        final Recipe RECIPE = new Recipe();
        final BigDecimal AMOUNT = new BigDecimal(1);
        final String DESCRIPTION = "Cheeseburger";
        final Long ID_VALUE = 1L;
        final Long UOM_ID = 2L;

        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
    }
}