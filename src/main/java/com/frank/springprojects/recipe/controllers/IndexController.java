package com.frank.springprojects.recipe.controllers;

import com.frank.springprojects.recipe.model.Category;
import com.frank.springprojects.recipe.model.UnitOfMeasure;
import com.frank.springprojects.recipe.repositories.CategoryRepository;
import com.frank.springprojects.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "index", "index.html"})
    public String getIndexPage() {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");

        System.out.printf("%-20s: %d%n", "Category ID",
                categoryOptional.isPresent() ? categoryOptional.get().getId() : -1);
        System.out.printf("%-20s: %d%n", "UnitOfMeasure ID",
                unitOfMeasureOptional.isPresent() ? unitOfMeasureOptional.get().getId() : -1);

        return "index";
    }
}