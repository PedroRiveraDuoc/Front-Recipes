package com.example.front_spring_recipes.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommentDtoTest {

    @Test
    void testGetterAndSetter() {
        CommentDto commentDto = new CommentDto();

        // Verificar valor inicial (null)
        assertNull(commentDto.getContent());

        // Establecer un valor y verificarlo
        String content = "This is a test comment.";
        commentDto.setContent(content);

        assertEquals(content, commentDto.getContent());
    }
}
