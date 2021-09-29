package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {
    Set<CategoryCommand> getAllCategoriesCommand();
}
