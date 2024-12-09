package com.example.front_spring_recipes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommentTest {

    @Test
    void testGettersAndSetters() {
        // Crear una instancia de Comment
        Comment comment = new Comment();

        // Verificar valores iniciales (null)
        assertNull(comment.getId());
        assertNull(comment.getContent());
        assertNull(comment.getUsername());
        assertNull(comment.getCreatedAt());

        // Asignar valores
        Long id = 1L;
        String content = "This is a test comment.";
        String username = "test_user";
        String createdAt = "2024-12-08T20:00:00";

        comment.setId(id);
        comment.setContent(content);
        comment.setUsername(username);
        comment.setCreatedAt(createdAt);

        // Verificar que los valores se asignan correctamente
        assertEquals(id, comment.getId());
        assertEquals(content, comment.getContent());
        assertEquals(username, comment.getUsername());
        assertEquals(createdAt, comment.getCreatedAt());
    }
}
