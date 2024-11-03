package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.model.Photo;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // Obtener todas las recetas
    @GetMapping
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes";
    }

    // Obtener una receta por ID
    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isPresent()) {
            model.addAttribute("recipe", recipeOpt.get());
            return "recipe_detail";
        } else {
            return "404";
        }
    }

    // Mostrar formulario para crear una nueva receta
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("isEdit", false);
        return "recipe_form";
    }

    // Crear una nueva receta
    @PostMapping
    public String createRecipe(
            @ModelAttribute("recipe") Recipe recipe,
            @RequestParam("photoUrl") String photoUrl,
            @RequestParam("photoDescription") String photoDescription,
            RedirectAttributes redirectAttributes) {

        Photo photo = new Photo();
        photo.setUrl(photoUrl);
        photo.setDescription(photoDescription);
        recipe.setPhoto(photo);

        recipeService.addRecipe(recipe);
        redirectAttributes.addFlashAttribute("successMessage", "Receta creada exitosamente.");
        return "redirect:/recipes";
    }

    // Mostrar formulario para editar una receta existente
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isPresent()) {
            model.addAttribute("recipe", recipeOpt.get());
            model.addAttribute("isEdit", true);
            return "recipe_form";
        } else {
            return "404";
        }
    }

    // Actualizar una receta existente
    @PostMapping("/edit/{id}")
    public String updateRecipe(
            @PathVariable Long id,
            @ModelAttribute("recipe") Recipe updatedRecipe,
            @RequestParam("photoUrl") String photoUrl,
            @RequestParam("photoDescription") String photoDescription,
            RedirectAttributes redirectAttributes) {

        Optional<Recipe> existingRecipeOpt = recipeService.getRecipeById(id);
        if (existingRecipeOpt.isPresent()) {
            Recipe existingRecipe = existingRecipeOpt.get();

            // Actualizar detalles de la receta
            existingRecipe.setTitle(updatedRecipe.getTitle());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setCookTime(updatedRecipe.getCookTime());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setInstructions(updatedRecipe.getInstructions());
            existingRecipe.setDifficulty(updatedRecipe.getDifficulty());

            // Actualizar foto
            Photo photo = existingRecipe.getPhoto() != null ? existingRecipe.getPhoto() : new Photo();
            photo.setUrl(photoUrl);
            photo.setDescription(photoDescription);
            existingRecipe.setPhoto(photo);

            recipeService.updateRecipe(existingRecipe);
            redirectAttributes.addFlashAttribute("successMessage", "Receta actualizada exitosamente.");
            return "redirect:/recipes/" + id;
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Receta no encontrada.");
            return "redirect:/recipes";
        }
    }

    // Eliminar una receta
    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recipeService.deleteRecipe(id);
        redirectAttributes.addFlashAttribute("successMessage", "Receta eliminada exitosamente.");
        return "redirect:/recipes";
    }
}