package com.example.front_spring_recipes.service;

import com.example.front_spring_recipes.model.Photo;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.repository.PhotoRepository;
import com.example.front_spring_recipes.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private PhotoRepository photoRepository;

    // Método para obtener todas las recetas
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // Método para obtener una receta por ID
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // Método para agregar una nueva receta
    public Recipe addRecipe(Recipe recipe) {
        // Guardar las fotos primero
        List<Photo> savedPhotos = recipe.getPhotos().stream()
                .map(photo -> photoRepository.save(photo)) // Guardar cada foto individualmente
                .collect(Collectors.toList());

        // Asignar las fotos guardadas a la receta
        recipe.setPhotos(savedPhotos);

        // Guardar la receta con las fotos asociadas
        return recipeRepository.save(recipe);
    }

    // Método para actualizar una receta existente
    public Optional<Recipe> updateRecipe(Long id, Recipe newRecipe) {
        return recipeRepository.findById(id).map(recipe -> {
            recipe.setTitle(newRecipe.getTitle());
            recipe.setDescription(newRecipe.getDescription());
            recipe.setIngredients(newRecipe.getIngredients());
            recipe.setInstructions(newRecipe.getInstructions());
            recipe.setCookTime(newRecipe.getCookTime());
            recipe.setDifficulty(newRecipe.getDifficulty());
            recipe.setPhotos(newRecipe.getPhotos());
            return recipeRepository.save(recipe);
        });
    }

    // Método para eliminar una receta por ID
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
