package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.commands.IngredientCommand;
import com.frank.springprojects.recipe.services.IngredientService;
import com.frank.springprojects.recipe.services.RecipeService;
import com.frank.springprojects.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable Long id, Model model) {
        log.debug("Getting ingredient lists for recipe " + id + "...");

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable Long recipeId,
                                 @PathVariable Long ingredientId, Model model) {
        log.debug("Getting ingredient (id) " + ingredientId
                + " of recipe (id) " + recipeId + "...");

        IngredientCommand ingredient = ingredientService
                .findCommandByRecipeIdAndIngredientId(recipeId, ingredientId);

        model.addAttribute("ingredient", ingredient);

        return "recipe/ingredient/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeingredient(@PathVariable Long recipeId,
                                         @PathVariable Long ingredientId, Model model) {


        model.addAttribute("ingredient",
                ingredientService.findCommandByRecipeIdAndIngredientId(recipeId, ingredientId));

        model.addAttribute("unitOfMeasureList", unitOfMeasureService.listAllUnitOfMeasures());

        return "recipe/ingredient/form";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/%d/ingredient/%d/show".formatted(savedCommand.getRecipeId(), savedCommand.getId());
    }
}