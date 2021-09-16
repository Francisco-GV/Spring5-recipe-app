package com.frank.springprojects.recipe.converters.recipe;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.converters.category.CategoryCommandToCategory;
import com.frank.springprojects.recipe.converters.ingredient.IngredientCommandToIngredient;
import com.frank.springprojects.recipe.converters.notes.NotesCommandToNotes;
import com.frank.springprojects.recipe.model.Notes;
import com.frank.springprojects.recipe.model.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Recipe convert(@Nullable RecipeCommand source) {
        if (source == null) return null;

        Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPreparationTime(source.getPreparationTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());

        Notes notes = notesConverter.convert(source.getNotes());
        if (notes != null) {
            notes.setRecipe(recipe);
            recipe.setNotes(notes);
        }

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