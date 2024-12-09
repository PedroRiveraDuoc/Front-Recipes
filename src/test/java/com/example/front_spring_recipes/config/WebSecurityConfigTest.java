package com.example.front_spring_recipes.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;

public class WebSecurityConfigTest {

    @Mock
    private CustomAuthenticationProvider authProvider;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @InjectMocks
    private WebSecurityConfig webSecurityConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        when(httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)).thenReturn(authenticationManagerBuilder);
        webSecurityConfig.authenticationManager(httpSecurity);
        verify(authenticationManagerBuilder).authenticationProvider(authProvider);
    }

@Test
void testSecurityFilterChain() throws Exception {
    // Configurar mocks para los métodos encadenados de HttpSecurity
    when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
    when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
    when(httpSecurity.formLogin(any())).thenReturn(httpSecurity);
    when(httpSecurity.logout(any())).thenReturn(httpSecurity);
    when(httpSecurity.build()).thenReturn(mock(DefaultSecurityFilterChain.class)); // Simula el DefaultSecurityFilterChain

    // Llamar al método que configura la cadena de filtros
    SecurityFilterChain filterChain = webSecurityConfig.securityFilterChain(httpSecurity);

    // Verificaciones
    assertNotNull(filterChain); // Asegurarse de que la cadena de seguridad no sea nula
    verify(httpSecurity).csrf(any());
    verify(httpSecurity).authorizeHttpRequests(any());
    verify(httpSecurity).formLogin(any());
    verify(httpSecurity).logout(any());
}

    @Test
    public void testCookieSerializer() {
        CookieSerializer cookieSerializer = webSecurityConfig.cookieSerializer();
        assert (cookieSerializer != null);
    }

    @Test
    public void testApplicationCookieSameSiteSupplier() {
        assert (webSecurityConfig.applicationCookieSameSiteSupplier() != null);
    }
}
