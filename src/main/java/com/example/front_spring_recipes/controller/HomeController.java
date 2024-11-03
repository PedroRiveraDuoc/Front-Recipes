package com.example.front_spring_recipes.controller;

import com.example.front_spring_recipes.model.Recipe;
import com.example.front_spring_recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/home")
    public String home(Model model) {
        // Obtener todas las recetas
        List<Recipe> allRecipes = recipeService.getAllRecipes();

        // Mezclar las recetas para aleatorizar
        Collections.shuffle(allRecipes);

        // Seleccionar las primeras tres recetas después de mezclar
        List<Recipe> recetasAleatorias = allRecipes.stream().limit(3).toList();

        // Añadir las recetas aleatorias al modelo
        model.addAttribute("recetasAleatorias", recetasAleatorias);

        return "home";
    }
}
