package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Photo;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes";
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "recipe_detail";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("isEdit", false);
        return "recipe_form";
    }

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

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        model.addAttribute("isEdit", true);
        return "recipe_form";
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(
            @PathVariable Long id,
            @ModelAttribute("recipe") Recipe updatedRecipe,
            @RequestParam("photoUrl") String photoUrl,
            @RequestParam("photoDescription") String photoDescription,
            RedirectAttributes redirectAttributes) {

        Photo photo = new Photo();
        photo.setUrl(photoUrl);
        photo.setDescription(photoDescription);
        updatedRecipe.setPhoto(photo);

        recipeService.updateRecipe(id, updatedRecipe);
        redirectAttributes.addFlashAttribute("successMessage", "Receta actualizada exitosamente.");
        return "redirect:/recipes/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        recipeService.deleteRecipe(id);
        redirectAttributes.addFlashAttribute("successMessage", "Receta eliminada exitosamente.");
        return "redirect:/recipes";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @RequestParam String content,
            RedirectAttributes redirectAttributes) {
        CommentDto comment = new CommentDto();
        comment.setContent(content);
        recipeService.createCommentByRecipeId(id, comment);
        redirectAttributes.addFlashAttribute("successMessage", "Comentario agregado exitosamente.");
        return "redirect:/recipes/" + id;
    }

}