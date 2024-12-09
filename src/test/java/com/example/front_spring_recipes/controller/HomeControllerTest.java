package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.config.TokenStore;
import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeControllerTest {

    @Mock
    private TokenStore tokenStore;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private HomeController homeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testRedirectToHome() {
        RedirectView redirectView = homeController.redirectToHome();
        assertEquals("/home", redirectView.getUrl());
    }

    @Test
    void testHome() {
        // Configurar mocks para la autenticación
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

        // Configurar recetas simuladas
        Recipe recipe1 = new Recipe();
        recipe1.setTitle("Recipe 1");
        Recipe recipe2 = new Recipe();
        recipe2.setTitle("Recipe 2");
        Recipe recipe3 = new Recipe();
        recipe3.setTitle("Recipe 3");
        List<Recipe> mockRecipes = Arrays.asList(recipe1, recipe2, recipe3);

        when(recipeService.getRecipes()).thenReturn(mockRecipes);

        // Ejecutar el método home
        String viewName = homeController.home(model);

        // Verificaciones
        verify(securityContext, times(1)).getAuthentication();
        verify(authentication, times(1)).getName();
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recetasAleatorias"), anyList());
        verify(model, times(1)).addAttribute("username", "testUser");

        assertEquals("home", viewName);
    }

    @Test
    void testLogin() {
        String viewName = homeController.login();
        assertEquals("login", viewName);
    }
}
