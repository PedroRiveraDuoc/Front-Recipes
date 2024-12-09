package com.example.front_spring_recipes.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    void testDefaultConstructor() {
        // Crear una instancia utilizando el constructor vacío
        Rating rating = new Rating();

        // Verificar que la instancia no sea nula
        assertNotNull(rating);

        // Verificar valores iniciales por defecto
        assertNull(rating.getId());
        assertNull(rating.getRecipe());
        assertEquals(0, rating.getValue());
        assertNull(rating.getUser());
        assertNull(rating.getDate());
    }

    @Test
    void testParameterizedConstructor() {
        Recipe mockRecipe = new Recipe();
        String user = "testUser";
        int value = 5;

        // Crear una instancia utilizando el constructor con parámetros
        Rating rating = new Rating(mockRecipe, value, user);

        // Verificar que los valores se asignen correctamente
        assertEquals(mockRecipe, rating.getRecipe());
        assertEquals(value, rating.getValue());
        assertEquals(user, rating.getUser());
        assertNotNull(rating.getDate());
    }

    @Test
    void testSettersAndGetters() {
        Rating rating = new Rating();

        Recipe mockRecipe = new Recipe();
        String user = "testUser";
        int value = 4;
        LocalDateTime date = LocalDateTime.now();

        // Asignar valores utilizando los setters
        rating.setId(1L);
        rating.setRecipe(mockRecipe);
        rating.setValue(value);
        rating.setUser(user);
        rating.setDate(date);

        // Verificar que los getters devuelvan los valores asignados
        assertEquals(1L, rating.getId());
        assertEquals(mockRecipe, rating.getRecipe());
        assertEquals(value, rating.getValue());
        assertEquals(user, rating.getUser());
        assertEquals(date, rating.getDate());
    }

    @Test
    void testSetValueWithValidRange() {
        Rating rating = new Rating();
        rating.setValue(3);

        assertEquals(3, rating.getValue());
    }

    @Test
    void testSetValueWithInvalidRange() {
        Rating rating = new Rating();

        // Probar valor menor al rango permitido
        Exception exceptionLow = assertThrows(IllegalArgumentException.class, () -> rating.setValue(0));
        assertEquals("La calificación debe estar entre 1 y 5.", exceptionLow.getMessage());

        // Probar valor mayor al rango permitido
        Exception exceptionHigh = assertThrows(IllegalArgumentException.class, () -> rating.setValue(6));
        assertEquals("La calificación debe estar entre 1 y 5.", exceptionHigh.getMessage());
    }
}
