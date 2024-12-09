package com.example.front_spring_recipes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DifficultyTest {

    @Test
    void testGetDisplayName() {
        // Verificar los valores asociados a cada constante
        assertEquals("FÁCIL", Difficulty.EASY.getDisplayName());
        assertEquals("MEDIANA", Difficulty.MEDIUM.getDisplayName());
        assertEquals("DIFÍCIL", Difficulty.HARD.getDisplayName());
    }

    @Test
    void testEnumValues() {
        // Verificar la cantidad de constantes en la enumeración
        Difficulty[] values = Difficulty.values();
        assertEquals(3, values.length);

        // Verificar que las constantes sean las esperadas
        assertEquals(Difficulty.EASY, values[0]);
        assertEquals(Difficulty.MEDIUM, values[1]);
        assertEquals(Difficulty.HARD, values[2]);
    }

    @Test
    void testValueOf() {
        // Verificar la recuperación de constantes a partir de su nombre
        assertEquals(Difficulty.EASY, Difficulty.valueOf("EASY"));
        assertEquals(Difficulty.MEDIUM, Difficulty.valueOf("MEDIUM"));
        assertEquals(Difficulty.HARD, Difficulty.valueOf("HARD"));
    }
}
