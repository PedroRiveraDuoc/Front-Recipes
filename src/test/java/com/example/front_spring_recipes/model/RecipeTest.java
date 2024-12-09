package com.example.front_spring_recipes.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void testDefaultConstructor() {
        Recipe recipe = new Recipe();

        assertNotNull(recipe);
        assertNull(recipe.getId());
        assertNull(recipe.getTitle());
        assertNull(recipe.getDescription());
        assertNull(recipe.getCookTime());
        assertNull(recipe.getDifficulty());
        assertNotNull(recipe.getIngredients());
        assertNotNull(recipe.getComments());
        assertNotNull(recipe.getRatings());
        assertNotNull(recipe.getPhoto());
    }

    @Test
    void testSettersAndGetters() {
        Recipe recipe = new Recipe();

        recipe.setId(1L);
        recipe.setTitle("Test Recipe");
        recipe.setDescription("This is a test recipe");
        recipe.setCookTime(30);
        recipe.setDifficulty(Difficulty.MEDIUM);

        List<String> ingredients = new ArrayList<>();
        ingredients.add("Flour");
        ingredients.add("Water");
        recipe.setIngredients(ingredients);

        recipe.setInstructions("Mix ingredients and cook.");
        Photo photo = new Photo(1L, "http://example.com/photo.jpg", "Test Photo");
        recipe.setPhoto(photo);

        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setContent("Great recipe!");
        comments.add(comment);
        recipe.setComments(comments);

        List<Rating> ratings = new ArrayList<>();
        Rating rating = new Rating();
        rating.setValue(5);
        ratings.add(rating);
        recipe.setRatings(ratings);

        assertEquals(1L, recipe.getId());
        assertEquals("Test Recipe", recipe.getTitle());
        assertEquals("This is a test recipe", recipe.getDescription());
        assertEquals(30, recipe.getCookTime());
        assertEquals(Difficulty.MEDIUM, recipe.getDifficulty());
        assertEquals(ingredients, recipe.getIngredients());
        assertEquals("Mix ingredients and cook.", recipe.getInstructions());
        assertEquals(photo, recipe.getPhoto());
        assertEquals(comments, recipe.getComments());
        assertEquals(ratings, recipe.getRatings());
    }

    @Test
    void testGetAverageRatingWithRatings() {
        Recipe recipe = new Recipe();

        List<Rating> ratings = new ArrayList<>();
        Rating rating1 = new Rating();
        rating1.setValue(4);
        ratings.add(rating1);

        Rating rating2 = new Rating();
        rating2.setValue(5);
        ratings.add(rating2);

        recipe.setRatings(ratings);

        assertEquals(4.5, recipe.getAverageRating());
    }

    @Test
    void testGetAverageRatingWithNoRatings() {
        Recipe recipe = new Recipe();

        assertEquals(0.0, recipe.getAverageRating());
    }

    @Test
    void testAddIngredients() {
        Recipe recipe = new Recipe();
        recipe.setIngredients(new ArrayList<>());

        recipe.getIngredients().add("Sugar");
        recipe.getIngredients().add("Salt");

        assertEquals(2, recipe.getIngredients().size());
        assertTrue(recipe.getIngredients().contains("Sugar"));
        assertTrue(recipe.getIngredients().contains("Salt"));
    }

    @Test
    void testAddComments() {
        Recipe recipe = new Recipe();
        recipe.setComments(new ArrayList<>());

        Comment comment = new Comment();
        comment.setContent("Amazing recipe!");

        recipe.getComments().add(comment);

        assertEquals(1, recipe.getComments().size());
        assertEquals("Amazing recipe!", recipe.getComments().get(0).getContent());
    }

    @Test
    void testAddRatings() {
        Recipe recipe = new Recipe();
        recipe.setRatings(new ArrayList<>());

        Rating rating = new Rating();
        rating.setValue(3);

        recipe.getRatings().add(rating);

        assertEquals(1, recipe.getRatings().size());
        assertEquals(3, recipe.getRatings().get(0).getValue());
    }
}
