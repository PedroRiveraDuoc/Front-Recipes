package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.model.Photo;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // Endpoint para obtener todas las recetas
    @GetMapping
    public String getAllRecipes(Model model) {
        List<Recipe> recipes = recipeService.getAllRecipes();
        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    // Endpoint para obtener una receta por ID
    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        if (recipe.isPresent()) {
            model.addAttribute("recipe", recipe.get());
            return "recipe_detail"; // Devolver la vista de detalles de la receta
        } else {
            return "404";
        }
    }

    // Mostrar formulario para crear nueva receta
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipe", new Recipe()); // Añadir una receta vacía al modelo
        return "recipe_form"; // Devuelve la vista del formulario
    }

    // Manejar la creación de una nueva receta
    @PostMapping
    public String createRecipe(@ModelAttribute("recipe") Recipe recipe,
            @RequestParam("photoUrl") List<String> photoUrls,
            @RequestParam("photoDescription") List<String> photoDescriptions) {

        // Procesar ingredientes
        recipe.setIngredients(List.of(recipe.getIngredients().get(0).split(",")));

        // Procesar fotos
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < photoUrls.size(); i++) {
            photos.add(new Photo(null, photoUrls.get(i), photoDescriptions.get(i)));
        }
        recipe.setPhotos(photos);

        recipeService.addRecipe(recipe);
        return "redirect:/recipes";
    }
}
