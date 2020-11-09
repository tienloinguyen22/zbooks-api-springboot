package com.neoflies.zbooks.domains.auth;

import com.neoflies.zbooks.configs.JwtConfigs;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class JwtTokenVerifier extends OncePerRequestFilter {
  @Autowired
  JwtConfigs jwtConfigs;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || authorizationHeader.isBlank() || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
    }

    try {
      String jwtToken = authorizationHeader.replace("Bearer ", "");
      Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(this.jwtConfigs.getSecretKey().getBytes())).build().parseClaimsJws(jwtToken);
      Claims body = claimsJws.getBody();
      String email = body.getSubject();
      List<Map<String, String>> authorities = (List<Map<String, String>> ) body.get("authorities");
      Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
      authorities.forEach(authority -> {
        grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("authority")));
      });
      Authentication authentication = new UsernamePasswordAuthenticationToken(
        email,
        null,
        grantedAuthorities
      );
      SecurityContextHolder.getContext().setAuthentication(authentication);
      filterChain.doFilter(request, response);
    } catch (JwtException e) {
      throw new IllegalStateException("Invalid token");
    }
  }
}
