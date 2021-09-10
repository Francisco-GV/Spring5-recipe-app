package com.frank.springprojects.recipe.converters.recipe;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.converters.category.CategoryToCategoryCommand;
import com.frank.springprojects.recipe.converters.ingredient.IngredientToIngredientCommand;
import com.frank.springprojects.recipe.converters.notes.NotesToNotesCommand;
import com.frank.springprojects.recipe.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConveter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConveter, IngredientToIngredientCommand ingredientConverter,
                                 NotesToNotesCommand notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public RecipeCommand convert(@Nullable Recipe source) {
        if (source == null) return null;

        RecipeCommand recipe = new RecipeCommand();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPreparationTime(source.getPreparationTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories()
                    .forEach(category -> recipe.getCategories().add(categoryConveter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }
}