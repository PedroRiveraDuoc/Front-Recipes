package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Photo;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    private static final String RECIPE_ATTRIBUTE = "recipe"; // Constante para "recipe"
    private static final String SUCCESS_MESSAGE_ATTRIBUTE = "successMessage"; // Constante para "successMessage"

    private final RecipeService recipeService;

    // Inyección por constructor
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes";
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.getRecipeById(id)); // Usar constante
        return "recipe_detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, new Recipe()); // Usar constante
        model.addAttribute("isEdit", false);
        return "recipe_form";
    }

    @PostMapping
    public String createRecipe(
            @ModelAttribute(RECIPE_ATTRIBUTE) Recipe recipe, // Usar constante
            @RequestParam("photoUrl") String photoUrl,
            @RequestParam("photoDescription") String photoDescription,
            RedirectAttributes redirectAttributes) {

        Photo photo = new Photo();
        photo.setUrl(photoUrl);
        photo.setDescription(photoDescription);
        recipe.setPhoto(photo);

        recipeService.addRecipe(recipe);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "Receta creada exitosamente."); // Usar constante
        return "redirect:/recipes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute(RECIPE_ATTRIBUTE, recipeService.getRecipeById(id)); // Usar constante
        model.addAttribute("isEdit", true);
        return "recipe_form";
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(
            @PathVariable Long id,
            @ModelAttribute(RECIPE_ATTRIBUTE) Recipe updatedRecipe, // Usar constante
            @RequestParam("photoUrl") String photoUrl,
            @RequestParam("photoDescription") String photoDescription,
            RedirectAttributes redirectAttributes) {

        Photo photo = new Photo();
        photo.setUrl(photoUrl);
        photo.setDescription(photoDescription);
        updatedRecipe.setPhoto(photo);

        recipeService.updateRecipe(id, updatedRecipe);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "Receta actualizada exitosamente."); // Usar constante
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recipeService.deleteRecipe(id);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "Receta eliminada exitosamente."); // Usar constante
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam String content,
            RedirectAttributes redirectAttributes) {
        CommentDto comment = new CommentDto();
        comment.setContent(content);
        recipeService.createCommentByRecipeId(id, comment);
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_ATTRIBUTE, "Comentario agregado exitosamente."); // Usar constante
        return "redirect:/recipes/" + id;
    }
}
