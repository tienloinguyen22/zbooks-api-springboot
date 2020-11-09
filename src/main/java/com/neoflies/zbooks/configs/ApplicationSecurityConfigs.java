package com.neoflies.zbooks.configs;

import com.neoflies.zbooks.domains.auth.JwtEmailAndPasswordAuthenticationFilter;
import com.neoflies.zbooks.domains.auth.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfigs extends WebSecurityConfigurerAdapter {
  @Autowired
  JwtConfigs jwtConfigs;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilter(new JwtEmailAndPasswordAuthenticationFilter(this.authenticationManager(), this.jwtConfigs))
      .addFilterAfter(new JwtTokenVerifier(), JwtEmailAndPasswordAuthenticationFilter.class)
      .authorizeRequests()
        .antMatchers("/login", "/auth/register").permitAll()
        .anyRequest().authenticated();
  }

}
