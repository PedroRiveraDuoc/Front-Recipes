package com.example.front_spring_recipes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PhotoTest {

    @Test
    void testDefaultConstructor() {
        // Crear una instancia utilizando el constructor vacío
        Photo photo = new Photo();

        // Verificar que la instancia no sea nula
        assertNotNull(photo);

        // Verificar valores iniciales por defecto
        assertEquals(null, photo.getId());
        assertEquals(null, photo.getUrl());
        assertEquals(null, photo.getDescription());
    }

    @Test
    void testParameterizedConstructor() {
        // Crear una instancia utilizando el constructor con parámetros
        Photo photo = new Photo(1L, "https://example.com/image.jpg", "Example description");

        // Verificar que los valores se asignen correctamente
        assertEquals(1L, photo.getId());
        assertEquals("https://example.com/image.jpg", photo.getUrl());
        assertEquals("Example description", photo.getDescription());
    }

    @Test
    void testSettersAndGetters() {
        // Crear una instancia de Photo
        Photo photo = new Photo();

        // Asignar valores utilizando los setters
        photo.setId(1L);
        photo.setUrl("https://example.com/image.jpg");
        photo.setDescription("Example description");

        // Verificar que los getters devuelvan los valores asignados
        assertEquals(1L, photo.getId());
        assertEquals("https://example.com/image.jpg", photo.getUrl());
        assertEquals("Example description", photo.getDescription());
    }
}
