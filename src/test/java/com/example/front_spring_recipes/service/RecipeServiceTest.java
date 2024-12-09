package com.example.front_spring_recipes.service;

import com.example.front_spring_recipes.config.TokenStore;
import com.example.front_spring_recipes.dto.CommentDto;
import com.example.front_spring_recipes.model.Rating;
import com.example.front_spring_recipes.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    @Mock
    private TokenStore tokenStore;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecipeService recipeService;

    private static final String BASE_URL = "http://localhost:8081/api/recipes";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Ahora, @InjectMocks se encargará de inyectar los mocks en RecipeService
    }

    @Test
    void testGetRecipes_Success() {
        // Arrange
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();
        Recipe[] recipesArray = { recipe1, recipe2 };
        ResponseEntity<Recipe[]> responseEntity = new ResponseEntity<>(recipesArray, HttpStatus.OK);
        when(restTemplate.exchange(
                eq(BASE_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Recipe[].class)
        )).thenReturn(responseEntity);

        // Act
        List<Recipe> recipes = recipeService.getRecipes();

        // Assert
        assertNotNull(recipes);
        assertEquals(2, recipes.size());
        verify(restTemplate, times(1)).exchange(
                eq(BASE_URL),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Recipe[].class)
        );
    }

    @Test
    void testGetRecipeById_Success() {
        // Arrange
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);
        ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(recipe, HttpStatus.OK);
        String url = BASE_URL + "/" + recipeId;
        when(tokenStore.getToken()).thenReturn("{\"token\":\"test-token\"}");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Recipe.class)
        )).thenReturn(responseEntity);

        // Act
        Recipe result = recipeService.getRecipeById(recipeId);

        // Assert
        assertNotNull(result);
        assertEquals(recipeId, result.getId());
        verify(restTemplate, times(1)).exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(Recipe.class)
        );
    }

    @Test
    void testAddRecipe_Success() {
        // Arrange
        Recipe newRecipe = new Recipe();
        newRecipe.setTitle("Test Recipe");
        ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
        when(tokenStore.getToken()).thenReturn("{\"token\":\"test-token\"}");
        when(restTemplate.exchange(
                eq(BASE_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Recipe.class)
        )).thenReturn(responseEntity);

        // Act
        Recipe result = recipeService.addRecipe(newRecipe);

        // Assert
        assertNotNull(result);
        assertEquals("Test Recipe", result.getTitle());
        verify(restTemplate, times(1)).exchange(
                eq(BASE_URL),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Recipe.class)
        );
    }

    @Test
    void testUpdateRecipe_Success() {
        // Arrange
        Long recipeId = 1L;
        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setTitle("Updated Recipe");
        ResponseEntity<Recipe> responseEntity = new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        String url = BASE_URL + "/" + recipeId;
        when(tokenStore.getToken()).thenReturn("{\"token\":\"test-token\"}");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Recipe.class)
        )).thenReturn(responseEntity);

        // Act
        Recipe result = recipeService.updateRecipe(recipeId, updatedRecipe);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Recipe", result.getTitle());
        verify(restTemplate, times(1)).exchange(
                eq(url),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(Recipe.class)
        );
    }

    @Test
    void testDeleteRecipe_Success() {
        // Arrange
        Long recipeId = 1L;
        String url = BASE_URL + "/" + recipeId;
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        when(tokenStore.getToken()).thenReturn("{\"token\":\"test-token\"}");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Void.class)
        )).thenReturn(responseEntity);

        // Act & Assert
        assertDoesNotThrow(() -> recipeService.deleteRecipe(recipeId));
        verify(restTemplate, times(1)).exchange(
                eq(url),
                eq(HttpMethod.DELETE),
                any(HttpEntity.class),
                eq(Void.class)
        );
    }

    @Test
    void testCreateCommentByRecipeId_Success() {
        // Arrange
        Long recipeId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setContent("Great recipe!");
        ResponseEntity<CommentDto> responseEntity = new ResponseEntity<>(commentDto, HttpStatus.CREATED);
        String url = BASE_URL + "/" + recipeId + "/comments";
        when(tokenStore.getToken()).thenReturn("{\"token\":\"test-token\"}");
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CommentDto.class)
        )).thenReturn(responseEntity);

        // Act
        CommentDto result = recipeService.createCommentByRecipeId(recipeId, commentDto);

        // Assert
        assertNotNull(result);
        assertEquals("Great recipe!", result.getContent());
        verify(restTemplate, times(1)).exchange(
                eq(url),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(CommentDto.class)
        );
    }

    @Test
    void testAddRating_Success() {
        // Arrange
        Long recipeId = 1L;
        int ratingValue = 5;
        String url = BASE_URL + "/" + recipeId + "/ratings";
        Rating rating = new Rating();
        rating.setValue(ratingValue);
        ResponseEntity<Rating> responseEntity = new ResponseEntity<>(rating, HttpStatus.CREATED);
        when(tokenStore.getToken()).thenReturn("{\"token\":\"test-token\"}");
        when(restTemplate.postForEntity(
                eq(url),
                any(HttpEntity.class),
                eq(Rating.class)
        )).thenReturn(responseEntity);

        // Act
        recipeService.addRating(recipeId, ratingValue);

        // Assert
        verify(restTemplate, times(1)).postForEntity(
                eq(url),
                any(HttpEntity.class),
                eq(Rating.class)
        );
    }

    // Casos de prueba adicionales para manejar excepciones y comportamientos alternativos pueden añadirse aquí
}
