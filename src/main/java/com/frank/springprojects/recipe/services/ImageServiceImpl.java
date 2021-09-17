package com.frank.springprojects.recipe.services;

import com.frank.springprojects.recipe.model.Recipe;
import com.frank.springprojects.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        log.debug("Received a file.");

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        try {
            byte[] bytes = new byte[file.getBytes().length];
            System.arraycopy(file.getBytes(), 0, bytes, 0, bytes.length);

            recipe.setImage(bytes);

            recipeRepository.save(recipe);
        } catch (IOException e) {
            log.error("An error occurred", e);
            e.printStackTrace();
        }
    }
}
