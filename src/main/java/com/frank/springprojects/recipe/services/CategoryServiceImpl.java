package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.commands.CategoryCommand;
import com.frank.springprojects.recipe.converters.category.CategoryToCategoryCommand;
import com.frank.springprojects.recipe.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    public Set<CategoryCommand> getAllCategoriesCommand() {
        log.debug("getting all categories...");

        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .map(categoryToCategoryCommand::convert)
                .collect(Collectors.toSet());
    }
}
