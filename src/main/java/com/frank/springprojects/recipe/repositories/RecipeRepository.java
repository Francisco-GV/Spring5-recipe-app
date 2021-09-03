package com.frank.springprojects.recipe.repositories;

import com.frank.springprojects.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
