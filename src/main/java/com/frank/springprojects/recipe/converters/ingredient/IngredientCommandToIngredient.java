package com.frank.springprojects.recipe.converters.ingredient;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.converters.unitofmeasure.UnitOfMeasureCommandToUnitOfMeasure;
import com.frank.springprojects.recipe.model.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public Ingredient convert(@Nullable IngredientCommand source) {
        if (source == null) return null;

        Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
        return ingredient;
    }
}