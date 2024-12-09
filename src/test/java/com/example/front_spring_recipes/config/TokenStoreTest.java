package com.example.front_spring_recipes.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenStoreTest {

    private TokenStore tokenStore;
    private Logger logger;

    @BeforeEach
    void setUp() {
        tokenStore = new TokenStore();
        logger = LoggerFactory.getLogger(TokenStore.class);
    }

    @Test
    void testSetToken() {
        String mockToken = "mockJWTToken";

        tokenStore.setToken(mockToken);
        logger.info("Token set: " + mockToken);

        assertEquals(mockToken, tokenStore.getToken());
    }

    @Test
    void testGetToken() {
        String mockToken = "mockJWTToken";

        tokenStore.setToken(mockToken);
        String retrievedToken = tokenStore.getToken();
        logger.info("Token retrieved: " + retrievedToken);

        assertEquals(mockToken, retrievedToken);
    }

    @Test
    void testClearToken() {
        String mockToken = "mockJWTToken";

        tokenStore.setToken(mockToken);
        tokenStore.clearToken();
        logger.info("Token cleared");

        assertNull(tokenStore.getToken());
    }

    @Test
    void testLogging() {
        Logger mockLogger = mock(Logger.class);
        TokenStore tokenStoreWithLogger = new TokenStore() {
            @Override
            public void setToken(String token) {
                super.setToken(token);
                mockLogger.info("Token set: " + token);
            }
        };

        String mockToken = "mockJWTToken";
        tokenStoreWithLogger.setToken(mockToken);

        verify(mockLogger).info("Token set: " + mockToken);
    }
}