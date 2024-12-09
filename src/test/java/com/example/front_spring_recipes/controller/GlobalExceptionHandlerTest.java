package com.example.front_spring_recipes.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private Model model;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleException() {
        Exception mockException = new Exception("Test exception");

        String viewName = globalExceptionHandler.handleException(mockException, model);

        verify(model, times(1)).addAttribute(eq("error"), eq("Se ha producido un error inesperado. Intente nuevamente más tarde."));
        verify(model, times(1)).addAttribute(eq("errorId"), anyString());
        assertEquals("error", viewName);
    }

    @SuppressWarnings("null")
    @Test
    void testHandleError() {
        ResponseEntity<String> response = globalExceptionHandler.handleError();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("text/html", response.getHeaders().getContentType() != null ? response.getHeaders().getContentType().toString() : null);
        assertEquals("<html><body><h3>Se ha producido un error inesperado.</h3></body></html>", response.getBody());
    }

    @Test
    void testHandleMissingResource() {
        @SuppressWarnings("null")
        NoResourceFoundException mockException = new NoResourceFoundException(null, "Resource not found");

        String viewName = globalExceptionHandler.handleMissingResource(mockException, model);

        verify(model, times(1)).addAttribute(eq("error"), eq("Resource not found."));
        assertEquals("error", viewName);
    }
}
