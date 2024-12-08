package com.example.front_spring_recipes.config;

import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;



@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    private static final String LOGIN_URL = "/login"; // Definir constante para "/login"

    private final CustomAuthenticationProvider authProvider;

    // Inyección mediante constructor
    public WebSecurityConfig(CustomAuthenticationProvider authProvider) {
        this.authProvider = authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
            .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(
                    "/",
                    "/**.css", 
                    LOGIN_URL, // Usar constante
                    "/home",
                    "/static/**", 
                    "/js/**", 
                    "/images/**"
                ).permitAll()
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage(LOGIN_URL) // Usar constante
                .loginProcessingUrl(LOGIN_URL) // Usar constante
                .defaultSuccessUrl("/home")
                .permitAll())
            .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookieName("JSESSIONID");
        cookieSerializer.setSameSite("Lax"); 
        cookieSerializer.setUseSecureCookie(true); 
        cookieSerializer.setCookiePath("/");
        return cookieSerializer;
    }

    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofLax();
    }

    @ControllerAdvice
    public class GlobalModelAttribute {
        @ModelAttribute
        public void addUsernameToModel(Model model, Authentication authentication) {
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                model.addAttribute("username", username);
            }
        }
    }
}
