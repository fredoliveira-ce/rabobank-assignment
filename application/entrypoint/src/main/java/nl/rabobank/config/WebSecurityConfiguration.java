package nl.rabobank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nl.rabobank.security.CustomAuthenticationProvider;
import nl.rabobank.security.filter.JwtAuthenticationFilter;
import nl.rabobank.security.filter.JwtLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final ObjectMapper mapper;
  private final CustomAuthenticationProvider authenticationProvider;

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    final var loginUrl = "/login";
    http.csrf().disable().authorizeRequests()
      .antMatchers("/api/**").authenticated()
      .and()
      .cors()
      .and()
      .addFilterBefore(
        new JwtLoginFilter(loginUrl, authenticationManager(), mapper), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(
        new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider);
  }

}
