package com.example.front_spring_recipes.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CustomAuthenticationProviderTest {

        @Mock
        private TokenStore tokenStore;

        @Mock
        private RestTemplate restTemplate;

        private CustomAuthenticationProvider customAuthenticationProvider;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);
                customAuthenticationProvider = new CustomAuthenticationProvider(tokenStore, restTemplate);
        }

        @Test
        void testAuthenticate_Success() {
                String mockToken = "{\"token\":\"mockToken\"}";
                String username = "testUser";
                String password = "testPassword";

                // Simular respuesta de RestTemplate
                ResponseEntity<String> responseEntity = new ResponseEntity<>(mockToken, HttpStatus.OK);
                when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                                .thenReturn(responseEntity);

                // Ejecutar el método authenticate
                Authentication result = customAuthenticationProvider.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password));

                // Verificar resultados
                assertNotNull(result);
                assertEquals(username, result.getName());
                assertEquals(password, result.getCredentials());
                assertTrue(result.getAuthorities().stream()
                                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

                // Verificar interacciones con dependencias
                verify(tokenStore, times(1)).setToken(mockToken);
        }

        @Test
        void testAuthenticate_InvalidCredentials() {
                String username = "testUser";
                String password = "wrongPassword";

                // Simular respuesta de error de RestTemplate
                ResponseEntity<String> responseEntity = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                                .thenReturn(responseEntity);

                // Ejecutar el método authenticate y esperar excepción
                assertThrows(BadCredentialsException.class, () -> customAuthenticationProvider.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password)));

                // Verificar que no se almacenó ningún token
                verify(tokenStore, never()).setToken(anyString());
        }

        @Test
        void testAuthenticate_ErrorResponse() {
                String username = "testUser";
                String password = "testPassword";

                // Simular excepción de RestTemplate
                when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(String.class)))
                                .thenThrow(new RuntimeException("Service error"));

                // Ejecutar el método authenticate y esperar excepción
                assertThrows(BadCredentialsException.class, () -> customAuthenticationProvider.authenticate(
                                new UsernamePasswordAuthenticationToken(username, password)));

                // Verificar que no se almacenó ningún token
                verify(tokenStore, never()).setToken(anyString());
        }

        @Test
        void testSupports() {
                assertTrue(customAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
        }
}
