package com.frank.springprojects.recipe.converters.notes;

import com.frank.springprojects.recipe.commands.NotesCommand;
import com.frank.springprojects.recipe.model.Notes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Override
    public NotesCommand convert(@Nullable Notes source) {
        if(source == null) return null;

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        return notesCommand;
    }
}