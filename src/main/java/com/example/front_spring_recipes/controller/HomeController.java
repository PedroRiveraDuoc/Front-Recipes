package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public RedirectView redirectToHome() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedUserName = auth.getName();
        // Obtener todas las recetas
        List<Recipe> allRecipes = recipeService.getAllRecipes();

        // Mezclar las recetas para aleatorizar
        Collections.shuffle(allRecipes);

        // Seleccionar las primeras tres recetas después de mezclar
        List<Recipe> recetasAleatorias = allRecipes.stream().limit(3).toList();

        // Añadir las recetas aleatorias al modelo
        model.addAttribute("recetasAleatorias", recetasAleatorias);
        System.out.println("usuario autenticado" + authenticatedUserName);

        model.addAttribute("username", authenticatedUserName);

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Asegúrate de que este nombre coincide con el archivo login.html en templates
    }

}
