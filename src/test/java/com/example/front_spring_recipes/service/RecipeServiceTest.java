package com.example.front_spring_recipes.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.example.front_spring_recipes.config.TokenStore;

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
}
