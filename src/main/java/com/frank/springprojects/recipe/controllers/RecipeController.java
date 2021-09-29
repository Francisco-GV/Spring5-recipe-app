package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.commands.CategoryCommand;
import com.frank.springprojects.recipe.commands.RecipeCommand;
import com.frank.springprojects.recipe.exceptions.NotFoundException;
import com.frank.springprojects.recipe.services.CategoryService;
import com.frank.springprojects.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    @Autowired
    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("categoryList")
    public Set<CategoryCommand> getCategories() {
        Set<CategoryCommand> categoryCommands = categoryService.getAllCategoriesCommand();
        log.debug("loaded categories: {}", categoryCommands);
        return categoryService.getAllCategoriesCommand();
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
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.error(objectError.toString()));
            return "recipe/form";
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/show/" + savedCommand.getId();
    }

    @GetMapping("/recipe/delete/{id}")
    public String delete(@RequestHeader(value = "referer", required = false) String referer,
                         @PathVariable Long id) {
        recipeService.deleteById(id);

        return "redirect:" + referer;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(Model model, Exception exception) {
        log.error("Handling NotFoundException: " + exception.getMessage());

        model.addAttribute("title", "404 - Not Found");
        model.addAttribute("message", exception.getMessage());

        return "error";
    }

}