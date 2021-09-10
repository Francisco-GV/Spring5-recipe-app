package com.frank.springprojects.recipe.converters.category;

import com.frank.springprojects.recipe.commands.CategoryCommand;
import com.frank.springprojects.recipe.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Override
    public CategoryCommand convert(@Nullable Category source) {
        if (source == null) return null;

        CategoryCommand category = new CategoryCommand();
        category.setId(source.getId());
        category.setDescription(source.getDescription());

        return category;
    }
}
