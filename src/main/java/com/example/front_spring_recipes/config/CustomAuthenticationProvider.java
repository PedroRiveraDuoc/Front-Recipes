
package com.example.front_spring_recipes.config;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private TokenStore tokenStore;

  public CustomAuthenticationProvider(TokenStore tokenStore) {
    super();
    this.tokenStore = tokenStore;
  }

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    final String name = authentication.getName();
    final String password = authentication.getCredentials().toString();

    // Crear el cuerpo de la solicitud como JSON
    JSONObject requestBody = new JSONObject();
    requestBody.put("username", name);
    requestBody.put("password", password);

    // Configurar encabezados para JSON
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    // Crear HttpEntity con el JSON como String
    HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

    // Realizar la solicitud
    final var restTemplate = new RestTemplate();
    final var responseEntity = restTemplate.postForEntity("http://localhost:8081/api/auth/login", entity,
        String.class);

    if (responseEntity.getStatusCode() != HttpStatus.OK) {
      throw new BadCredentialsException("Invalid username or password");
    }

    tokenStore.setToken(responseEntity.getBody());

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

    return new UsernamePasswordAuthenticationToken(name, password, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
