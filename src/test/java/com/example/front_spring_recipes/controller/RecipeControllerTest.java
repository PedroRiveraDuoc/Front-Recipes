package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Photo;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecipes() {
        when(recipeService.getRecipes()).thenReturn(List.of(new Recipe(), new Recipe()));

        String viewName = recipeController.getAllRecipes(model);

        assertEquals("recipes", viewName);
        verify(model, times(1)).addAttribute(eq("recipes"), any());
    }

    @Test
    void testGetRecipeById() {
        Recipe mockRecipe = new Recipe();
        when(recipeService.getRecipeById(1L)).thenReturn(mockRecipe);

        String viewName = recipeController.getRecipeById(1L, model);

        assertEquals("recipe_detail", viewName);
        verify(model, times(1)).addAttribute("recipe", mockRecipe);
    }

    @Test
    void testShowCreateForm() {
        String viewName = recipeController.showCreateForm(model);

        assertEquals("recipe_form", viewName);
        verify(model, times(1)).addAttribute(eq("recipe"), any(Recipe.class));
        verify(model, times(1)).addAttribute(eq("isEdit"), eq(false));
    }

    @Test
    void testCreateRecipe() {
        Recipe mockRecipe = new Recipe();
        String photoUrl = "http://example.com/photo.jpg";
        String photoDescription = "Sample photo";

        String viewName = recipeController.createRecipe(mockRecipe, photoUrl, photoDescription, redirectAttributes);

        assertEquals("redirect:/recipes", viewName);
        verify(recipeService, times(1)).addRecipe(mockRecipe);
        verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "Receta creada exitosamente.");
        assertNotNull(mockRecipe.getPhoto());
        assertEquals(photoUrl, mockRecipe.getPhoto().getUrl());
        assertEquals(photoDescription, mockRecipe.getPhoto().getDescription());
    }

    @Test
    void testShowEditForm() {
        Recipe mockRecipe = new Recipe();
        when(recipeService.getRecipeById(1L)).thenReturn(mockRecipe);

        String viewName = recipeController.showEditForm(1L, model);

        assertEquals("recipe_form", viewName);
        verify(model, times(1)).addAttribute("recipe", mockRecipe);
        verify(model, times(1)).addAttribute("isEdit", true);
    }

    @Test
    void testUpdateRecipe() {
        Recipe mockRecipe = new Recipe();
        String photoUrl = "http://example.com/photo.jpg";
        String photoDescription = "Sample photo";

        String viewName = recipeController.updateRecipe(1L, mockRecipe, photoUrl, photoDescription, redirectAttributes);

        assertEquals("redirect:/recipes/1", viewName);
        verify(recipeService, times(1)).updateRecipe(eq(1L), eq(mockRecipe));
        verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "Receta actualizada exitosamente.");
        assertNotNull(mockRecipe.getPhoto());
        assertEquals(photoUrl, mockRecipe.getPhoto().getUrl());
        assertEquals(photoDescription, mockRecipe.getPhoto().getDescription());
    }

    @Test
    void testDeleteRecipe() {
        String viewName = recipeController.deleteRecipe(1L, redirectAttributes);

        assertEquals("redirect:/recipes", viewName);
        verify(recipeService, times(1)).deleteRecipe(1L);
        verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "Receta eliminada exitosamente.");
    }

    @Test
    void testAddComment() {
        String content = "Great recipe!";
        CommentDto mockComment = new CommentDto();
        mockComment.setContent(content);
        when(recipeService.createCommentByRecipeId(eq(1L), any(CommentDto.class))).thenReturn(mockComment);

        String viewName = recipeController.addComment(1L, content, redirectAttributes);

        assertEquals("redirect:/recipes/1", viewName);
        verify(recipeService, times(1)).createCommentByRecipeId(eq(1L), any(CommentDto.class));
        verify(redirectAttributes, times(1)).addFlashAttribute("successMessage", "Comentario agregado exitosamente.");
    }
}
