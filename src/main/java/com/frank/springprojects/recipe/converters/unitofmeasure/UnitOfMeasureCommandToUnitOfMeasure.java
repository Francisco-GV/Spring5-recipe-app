package com.frank.springprojects.recipe.converters.unitofmeasure;

import com.frank.springprojects.recipe.commands.UnitOfMeasureCommand;
import com.frank.springprojects.recipe.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Override
    public UnitOfMeasure convert(@Nullable UnitOfMeasureCommand source) {
        if (source == null) return null;

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setDescription(source.getDescription());
        return unitOfMeasure;
    }
}
