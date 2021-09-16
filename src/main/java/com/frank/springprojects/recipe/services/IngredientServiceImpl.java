package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.converters.ingredient.IngredientCommandToIngredient;
import com.frank.springprojects.recipe.converters.ingredient.IngredientToIngredientCommand;
import com.frank.springprojects.recipe.model.Ingredient;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.model.UnitOfMeasure;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import com.frank.springprojects.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long id) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        return recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(id))
                .map(ingredientToIngredientCommand::convert)
                .findFirst().orElseThrow();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(@NonNull IngredientCommand ingredientCommand) {
        Objects.requireNonNull(ingredientCommand);

        Recipe recipe = recipeRepository.findById(ingredientCommand.getRecipeId()).orElseThrow();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            log.debug("Updating existing ingredient (recipeID={}, ingredientID={})...",
                    recipe.getId(), ingredientFound.getId());

            ingredientFound.setDescription(ingredientCommand.getDescription());
            ingredientFound.setAmount(ingredientCommand.getAmount());

            Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository
                    .findById(ingredientCommand.getUnitOfMeasure().getId());

            ingredientFound.setUnitOfMeasure(unitOfMeasureOptional.orElseThrow());
        } else {
            log.debug("Ingredient ({}) doesn't exists in recipe (id={}), adding...",
                    ingredientCommand.getDescription(), recipe.getId());
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            Objects.requireNonNull(ingredient).setRecipe(recipe);
            recipe.getIngredients().add(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        //check by properties
        if(savedIngredientOptional.isEmpty()){
            //not totally safe... But best guess
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                    .findFirst();
        }

        return ingredientToIngredientCommand.convert(savedIngredientOptional.orElseThrow());
    }

    @Transactional
    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("Deletting ingredient (id={}) from recipe {}...", ingredientId, recipeId);

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        // verify that recipe owns the ingredient
        Ingredient ingredientToDelete = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst().orElseThrow(() ->
                        new RuntimeException("Recipe (id=%d) doesn't own that ingredient (id=%d)!!!!!"
                                .formatted(recipeId, ingredientId)));

        log.debug("Ingredient to delete: {}", ingredientToDelete.toString());

        ingredientToDelete.setRecipe(null);
        recipe.getIngredients().remove(ingredientToDelete);

        recipeRepository.save(recipe);
    }
}