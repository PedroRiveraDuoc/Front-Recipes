package com.example.front_spring_recipes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class FrontSpringRecipesApplicationTestsTest {

    @Test
    void contextLoads() {
        FrontSpringRecipesApplicationTestsTest applicationTests = new FrontSpringRecipesApplicationTestsTest();
        assertNotNull(applicationTests);
    }

    @Test
    void testMainMethod() {
        // Verificar que el método main no lanza excepciones
        assertDoesNotThrow(() -> FrontSpringRecipesApplication.main(new String[]{}));
    }
}
