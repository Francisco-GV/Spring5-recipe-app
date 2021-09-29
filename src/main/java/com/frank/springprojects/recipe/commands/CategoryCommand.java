package com.frank.springprojects.recipe.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
