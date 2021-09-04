package com.frank.springprojects.recipe.boot;

import com.frank.springprojects.recipe.model.Category;
import com.frank.springprojects.recipe.model.Difficulty;
import com.frank.springprojects.recipe.model.Ingredient;
import com.frank.springprojects.recipe.model.Notes;
import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.model.UnitOfMeasure;
import com.frank.springprojects.recipe.repositories.CategoryRepository;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import com.frank.springprojects.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    @SuppressWarnings("unused")
    public List<Recipe> getRecipes() {
        Category mexicanCategory = getCategory("Mexican");
        Category americanCategory = getCategory("American");
        Category italianCategory = getCategory("Italian");
        Category fastFoodCategory = getCategory("Fast Food");

        UnitOfMeasure teaspoonUom = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure tablespoonUom = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure cupUom = getUnitOfMeasure("Cup");
        UnitOfMeasure pinchUom = getUnitOfMeasure("Pinch");
        UnitOfMeasure ounceUom = getUnitOfMeasure("Ounce");
        UnitOfMeasure eachUom = getUnitOfMeasure("Each");
        UnitOfMeasure dashUom = getUnitOfMeasure("Dash");
        UnitOfMeasure pintUom = getUnitOfMeasure("Pint");


        List<Recipe> recipes = new ArrayList<>(2);

        { // Guacamole
            Recipe guacamoleRecipe;

            String url = "https://www.simplyrecipes.com/recipes/perfect_guacamole/";

            String description = "Perfect Guacamole";
            Category[] category = {americanCategory, mexicanCategory};
            Difficulty difficulty = Difficulty.EASY;
            Integer preparationTime = 10;
            Integer cookTime = null;
            Integer servings = 4;

            Notes notes = new Notes();
            notes.setRecipeNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. " +
                    "If no gloves are available, wash your hands thoroughly after handling, and do not touch " +
                    "your eyes or the area near your eyes for several hours afterwards.");

            Ingredient[] ingredients = {
                    createIngredient(2, eachUom, "ripe avocados"),
                    createIngredient(0.5, teaspoonUom, "kosher salt"),
                    createIngredient(2, tablespoonUom, "fresh lime juice or lemon juice"),
                    createIngredient(2, tablespoonUom, "minced red onion or thinly sliced green onion"),
                    createIngredient(2, eachUom, "serrano chiles, stems and seeds removed, minced"),
                    createIngredient(2, tablespoonUom, "cilantro"),
                    createIngredient(2, dashUom, "freshly grated black pepper"),
                    createIngredient(0.5, eachUom, "ripe tomato, seeds and pulp removed, chopped"),
            };

            String directions = """
                    1.- Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon
                    2.- Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)
                    3.- Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.
                    Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.
                    Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.
                    4.- Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.
                    Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.
                    """;

            guacamoleRecipe = createRecipe(url, description, category, difficulty, preparationTime, cookTime, servings, notes, ingredients, directions);

            // recipeRepository.save(guacamoleRecipe);
            recipes.add(guacamoleRecipe);
        }

        { // Tacos
            Recipe tacosRecipe;

            String url = "https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/";

            String description = "Spicy Grilled Chicken Taco";
            Category[] category = {americanCategory, mexicanCategory};
            Difficulty difficulty = Difficulty.MODERATE;
            Integer preparationTime = 20;
            Integer cookTime = 15;
            Integer servings = 6;

            Notes notes = new Notes();
            notes.setRecipeNotes("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. " +
                    "Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");

            Ingredient[] ingredients = {
                    createIngredient(2, tablespoonUom, "Ancho Chili Powder"),
                    createIngredient(1, teaspoonUom, "Dried Oregano"),
                    createIngredient(1, teaspoonUom, "Dried Cumin"),
                    createIngredient(1, teaspoonUom, "Sugar"),
                    createIngredient(.5, teaspoonUom, "Salt"),
                    createIngredient(1, eachUom, "Clove of Garlic"),
                    createIngredient(1, tablespoonUom, "finely grated orange zestr"),
                    createIngredient(3, tablespoonUom, "fresh-squeezed orange juice"),
                    createIngredient(2, tablespoonUom, "Olive Oil"),
                    createIngredient(4, tablespoonUom, "boneless chicken thighs"),
                    createIngredient(8, eachUom, "small corn tortillas"),
                    createIngredient(3, cupUom, "packed baby arugula"),
                    createIngredient(2, eachUom, "medium ripe avocados, slic"),
                    createIngredient(4, eachUom, "radishes, thinly sliced"),
                    createIngredient(.5, pintUom, "cherry tomatoes, halved"),
                    createIngredient(.25, eachUom, "red onion, thinly sliced"),
                    createIngredient(4, eachUom, "Roughly chopped cilantro"),
                    createIngredient(4, cupUom, "cup sour cream thinned with 1/4 cup milk"),
                    createIngredient(4, eachUom, "lime, cut into wedges"),
            };

            String directions = """
                    1.- Prepare a gas or charcoal grill for medium-high, direct heat.
                    2.- Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.
                    Set aside to marinate while the grill heats and you prepare the rest of the toppings.
                    3.- Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.
                    4.- Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.
                    Wrap warmed tortillas in a tea towel to keep them warm until serving.
                    5.- Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.
                    """;

            tacosRecipe = createRecipe(url, description, category, difficulty, preparationTime, cookTime, servings, notes, ingredients, directions);

            // recipeRepository.save(tacosRecipe);
            recipes.add(tacosRecipe);
        }

        return recipes;
    }

    private Recipe createRecipe(String url, String description, Category[] categories, Difficulty difficulty,
                                Integer preparationTime, Integer cookTime, Integer servings,
                                Notes notes, Ingredient[] ingredients, String directions) {
        Recipe recipe = new Recipe();
        recipe.setUrl(url);

        recipe.setDescription(description);
        recipe.setCategories(new HashSet<>(List.of(categories)));
        recipe.setDifficulty(difficulty);

        recipe.setPreparationTime(preparationTime);
        recipe.setCookTime(cookTime);
        recipe.setServings(servings);

        recipe.setNotes(notes);
        notes.setRecipe(recipe);

        recipe.setIngredients(new HashSet<>(List.of(ingredients)));
        Arrays.asList(ingredients).forEach(ingredient -> ingredient.setRecipe(recipe));

        recipe.setDirections(directions);

        return recipe;
    }

    private Ingredient createIngredient(double amount, UnitOfMeasure unitOfMeasure, String description) {
        Ingredient ingredient = new Ingredient();

        ingredient.setAmount(new BigDecimal(amount));
        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setDescription(description);

        return ingredient;
    }

    private Category getCategory(String description) {
        return categoryRepository
                .findByDescription(description)
                .orElseThrow(() ->
                        new NoSuchElementException("(category) " + description + " not found!"));
    }

    private UnitOfMeasure getUnitOfMeasure(String description) {
        return unitOfMeasureRepository
                .findByDescription(description)
                .orElseThrow(() ->
                        new NoSuchElementException("(UnitOfMeasure) " + description + " not found!"));
    }
}
