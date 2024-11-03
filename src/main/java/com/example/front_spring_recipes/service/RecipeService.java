package com.example.front_spring_recipes.service;

import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    // Obtener todas las recetas
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Obtener una receta por su ID
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // Crear una nueva receta
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // Actualizar una receta existente
    public void updateRecipe(Long id, Recipe updatedRecipe) {
        if (updatedRecipe == null) {
            throw new IllegalArgumentException("Updated recipe cannot be null");
        }
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);
        if (existingRecipe.isPresent()) {
            Recipe recipe = existingRecipe.get();

            // Asegurarse de que las listas sean mutables
            recipe.setTitle(updatedRecipe.getTitle());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setIngredients(new ArrayList<>(updatedRecipe.getIngredients())); // Lista mutable
            recipe.setInstructions(updatedRecipe.getInstructions());
            recipe.setCookTime(updatedRecipe.getCookTime());
            recipe.setDifficulty(updatedRecipe.getDifficulty());
            recipe.getPhotos().clear();
            recipe.getPhotos().addAll(updatedRecipe.getPhotos());

            recipeRepository.save(recipe); // Guardar la receta actualizada
        } else {
            throw new IllegalArgumentException("Recipe with ID " + id + " does not exist.");
        }
    }

    // Eliminar una receta por su ID
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new IllegalArgumentException("Recipe with ID " + id + " does not exist.");
        }
        recipeRepository.deleteById(id);
    }
}
