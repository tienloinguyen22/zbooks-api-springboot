package com.neoflies.zbooks.domains.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoflies.zbooks.configs.JwtConfigs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtEmailAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final JwtConfigs jwtConfigs;

  private final AuthenticationManager authenticationManager;

  public JwtEmailAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfigs jwtConfigs) {
    this.authenticationManager = authenticationManager;
    this.jwtConfigs = jwtConfigs;
    this.setFilterProcessesUrl("/auth/login");
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      EmailAndPasswordAuthenticationRequest emailAndPasswordAuthenticationRequest = new ObjectMapper().readValue(request.getInputStream(), EmailAndPasswordAuthenticationRequest.class);
      Authentication authentication = new UsernamePasswordAuthenticationToken(emailAndPasswordAuthenticationRequest.getEmail(), emailAndPasswordAuthenticationRequest.getPassword());
      return this.authenticationManager.authenticate(authentication);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String token = Jwts.builder()
      .setSubject(authResult.getName())
      .claim("authorities", authResult.getAuthorities())
      .setIssuedAt(new Date())
      .setExpiration(new Date(new Date().getTime() + this.jwtConfigs.getTokenValidation())) // 2 weeks
      .signWith(Keys.hmacShaKeyFor(this.jwtConfigs.getSecretKey().getBytes()))
      .compact();
    response.addHeader("authorization", "Bearer " + token);
  }
}
