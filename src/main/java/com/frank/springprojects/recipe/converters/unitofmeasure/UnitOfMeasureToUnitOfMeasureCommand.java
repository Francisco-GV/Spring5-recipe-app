package com.frank.springprojects.recipe.converters.unitofmeasure;

import com.frank.springprojects.recipe.commands.UnitOfMeasureCommand;
import com.frank.springprojects.recipe.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Override
    public UnitOfMeasureCommand convert(@Nullable UnitOfMeasure source) {
        if (source == null) return null;

        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(source.getId());
        unitOfMeasureCommand.setDescription(source.getDescription());
        return unitOfMeasureCommand;
    }
}
