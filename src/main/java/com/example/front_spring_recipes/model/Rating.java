package com.example.front_spring_recipes.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private int value; // Valor de la calificación (ejemplo: entre 1 y 5)

    @Column(nullable = false)
    private String user; // Usuario que realizó la calificación

    @Column(nullable = false)
    private LocalDateTime date; // Fecha de la calificación

    // Constructor por defecto
    public Rating() {
    }

    // Constructor con parámetros
    public Rating(Recipe recipe, int value, String user) {
        this.recipe = recipe;
        this.value = value;
        this.user = user;
        this.date = LocalDateTime.now();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5.");
        }
        this.value = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
