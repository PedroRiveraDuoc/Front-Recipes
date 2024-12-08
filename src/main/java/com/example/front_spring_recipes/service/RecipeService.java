package com.example.front_spring_recipes.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.front_spring_recipes.config.TokenStore;
import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Recipe;

@Service
public class RecipeService {

    @Autowired
    private TokenStore tokenStore;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl = "http://localhost:8081/api/recipes";

    private HttpHeaders createHeaders(boolean requireAuth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (requireAuth) {
            String rawTokenObject = tokenStore.getToken();
            if (rawTokenObject == null || rawTokenObject.isEmpty()) {
                throw new IllegalStateException("Token no disponible. El usuario debe autenticarse.");
            }
            String token = new JSONObject(rawTokenObject).getString("token");
            headers.set("Authorization", "Bearer " + token);
        }

        return headers;
    }

    // Obtener todas las recetas (público)
    public List<Recipe> getRecipes() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(false));
        ResponseEntity<Recipe[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Recipe[].class);
        return Arrays.asList(response.getBody());
    }

    // Obtener una receta por su ID (requiere autenticación)
    public Recipe getRecipeById(Long id) {
        String url = baseUrl + "/" + id;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(true));
        ResponseEntity<Recipe> response = restTemplate.exchange(url, HttpMethod.GET, entity, Recipe.class);
        return response.getBody();
    }

    // Crear una nueva receta (requiere autenticación)
    public Recipe addRecipe(Recipe recipe) {
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, createHeaders(true));
        ResponseEntity<Recipe> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, Recipe.class);
        return response.getBody();
    }

    // Actualizar una receta existente (requiere autenticación)
    public Recipe updateRecipe(Long id, Recipe recipe) {
        String url = baseUrl + "/" + id;
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, createHeaders(true));
        ResponseEntity<Recipe> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Recipe.class);
        return response.getBody();
    }

    // Eliminar una receta por su ID (requiere autenticación)
    public void deleteRecipe(Long id) {
        String url = baseUrl + "/" + id;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(true));
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public CommentDto createCommentByRecipeId(Long id, CommentDto comment) {
        String url = baseUrl + "/" + id + "/comments";
        HttpEntity<CommentDto> entity = new HttpEntity<>(comment, createHeaders(true));
        ResponseEntity<CommentDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, CommentDto.class);
        return response.getBody();
    }
}