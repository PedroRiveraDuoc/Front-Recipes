package com.example.front_spring_recipes.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.example.front_spring_recipes.config.TokenStore;
import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Rating;
import com.example.front_spring_recipes.model.Recipe;

public class RecipeServiceTest {

    @Mock
    private TokenStore tokenStore;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateHeadersWithoutAuth() {
        HttpHeaders headers = recipeService.createHeaders(false);

        assertNotNull(headers);
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertNull(headers.get("Authorization"));
    }

    @Test
    void testCreateHeadersWithAuth() {
        when(tokenStore.getToken()).thenReturn("{\"token\":\"mockToken\"}");

        HttpHeaders headers = recipeService.createHeaders(true);

        assertNotNull(headers);
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals("Bearer mockToken", headers.getFirst("Authorization"));
    }

    @Test
    void testGetRecipes() {
        Recipe[] mockRecipes = new Recipe[] { new Recipe(), new Recipe() };
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(Recipe[].class)))
                .thenReturn(new ResponseEntity<>(mockRecipes, HttpStatus.OK));

        List<Recipe> recipes = recipeService.getRecipes();

        assertNotNull(recipes);
        assertEquals(2, recipes.size());
    }
}
