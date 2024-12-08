package com.example.front_spring_recipes.service;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.front_spring_recipes.config.TokenStore;
import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Rating;
import com.example.front_spring_recipes.model.Recipe;

@Service
public class RecipeService {

    private final TokenStore tokenStore;
    private final RestTemplate restTemplate = new RestTemplate();

    // Cambiar "final" a "static final" para indicar que es una constante de clase.
    private static final String BASE_URL = "http://localhost:8081/api/recipes";

    // Inyección por constructor
    public RecipeService(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public HttpHeaders createHeaders(boolean requireAuth) {
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

    // Métodos existentes que ahora usan BASE_URL en lugar de baseUrl
    public List<Recipe> getRecipes() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(false));
        ResponseEntity<Recipe[]> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, Recipe[].class);
        return Arrays.asList(response.getBody());
    }

    public Recipe getRecipeById(Long id) {
        String url = BASE_URL + "/" + id;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(true));
        ResponseEntity<Recipe> response = restTemplate.exchange(url, HttpMethod.GET, entity, Recipe.class);
        return response.getBody();
    }

    public Recipe addRecipe(Recipe recipe) {
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, createHeaders(true));
        ResponseEntity<Recipe> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, entity, Recipe.class);
        return response.getBody();
    }

    public Recipe updateRecipe(Long id, Recipe recipe) {
        String url = BASE_URL + "/" + id;
        HttpEntity<Recipe> entity = new HttpEntity<>(recipe, createHeaders(true));
        ResponseEntity<Recipe> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Recipe.class);
        return response.getBody();
    }

    public void deleteRecipe(Long id) {
        String url = BASE_URL + "/" + id;
        HttpEntity<String> entity = new HttpEntity<>(createHeaders(true));
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }

    public CommentDto createCommentByRecipeId(Long id, CommentDto comment) {
        String url = BASE_URL + "/" + id + "/comments";
        HttpEntity<CommentDto> entity = new HttpEntity<>(comment, createHeaders(true));
        ResponseEntity<CommentDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, CommentDto.class);
        return response.getBody();
    }

    public void addRating(Long recipeId, int ratingValue) {
        String url = BASE_URL + "/" + recipeId + "/ratings";
        Rating rating = new Rating();
        rating.setValue(ratingValue);
        HttpEntity<Rating> entity = new HttpEntity<>(rating, createHeaders(true));
        restTemplate.postForEntity(url, entity, Rating.class);
    }
}
