package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);
    RecipeCommand findCommandById(Long id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    void deleteById(Long id);
}
