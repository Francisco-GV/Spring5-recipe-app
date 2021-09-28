CREATE VIEW recipe_1_ingredients AS
    SELECT
       recipe.description AS recipe, ingredient.id, ingredient.amount, ingredient.description
    FROM
         ingredient
             JOIN
             recipe ON ingredient.recipe_id = recipe.id
    WHERE recipe_id = 1;