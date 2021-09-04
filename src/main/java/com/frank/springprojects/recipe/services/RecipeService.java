package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}
