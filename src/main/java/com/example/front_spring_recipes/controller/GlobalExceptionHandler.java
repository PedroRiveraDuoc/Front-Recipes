package com.example.front_spring_recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        // Log the detailed exception for internal purposes only
        ex.printStackTrace(); // Puedes usar un logger aquí

        // Set a generic error message to avoid revealing sensitive info
        model.addAttribute("error", "Se ha producido un error inesperado. Intente nuevamente más tarde.");
        return "error"; // Un archivo HTML en templates para mostrar un mensaje de error
    }
}
