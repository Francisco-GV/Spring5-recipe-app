package com.frank.springprojects.recipe.converters.category;

import com.frank.springprojects.recipe.commands.CategoryCommand;
import com.frank.springprojects.recipe.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Override
    public Category convert(@Nullable CategoryCommand source) {
        if (source == null) return null;

        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());

        return category;
    }
}
