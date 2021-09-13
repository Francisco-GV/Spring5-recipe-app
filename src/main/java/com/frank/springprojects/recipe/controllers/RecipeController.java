package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    private String showById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/show";
    }

    @RequestMapping("/recipe/new")
    private String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/form";
    }

    @RequestMapping("/recipe/update/{id}")
    private String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/form";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }

    @GetMapping("/recipe/delete/{id}")
    public String delete(@RequestHeader(value = "referer", required = false) String referer,
                         @PathVariable Long id) {
        recipeService.deleteById(id);

        return "redirect:" + referer;
    }
}