package com.frank.springprojects.recipe.converters.notes;

import com.frank.springprojects.recipe.commands.NotesCommand;
import com.frank.springprojects.recipe.model.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {
    @Override
    public Notes convert(@Nullable NotesCommand source) {
        if(source == null) return null;

        Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}