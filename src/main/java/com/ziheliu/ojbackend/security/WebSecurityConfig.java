package com.ziheliu.ojbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String AUTHORITY_ADMIN = "ADMIN";

  private final CustomerUserService userService;

  @Autowired
  public WebSecurityConfig(CustomerUserService userService) {
    this.userService = userService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setHideUserNotFoundExceptions(false);
    provider.setUserDetailsService(userService);
    provider.setPasswordEncoder(new BCryptPasswordEncoder());
    auth.authenticationProvider(provider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/users/**").permitAll()
      .antMatchers("/admin/**").hasAnyAuthority(AUTHORITY_ADMIN)
      .anyRequest().authenticated()

      .and().formLogin()
      .loginPage("/users/login")
      .failureForwardUrl("/users/login/fail")
      .successForwardUrl("/users/login/success")
      .usernameParameter("username")
      .passwordParameter("password")
      .permitAll()

      .and().logout()
      .logoutUrl("/users/logout")
      .logoutSuccessUrl("/users/login/success")
      .permitAll()

      .and().csrf().disable()

      .rememberMe()
      .tokenRepository(this.persistentTokenRepository())
      .tokenValiditySeconds(31536000);
  }

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    // TODO: Redis
    return new InMemoryTokenRepositoryImpl();
  }
}
