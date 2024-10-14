package com.example.backend.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a configuration class for Spring
public class WebSecurityConfig {

    /*
     * A bean is simply a Java object that is instantiated, assembled, and managed
     * by the Spring IoC container.
     */
    @Bean // Defines a bean for UserDetailsService
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(); // Custom implementation of UserDetailsService
    }

    @Bean // Defines a bean for PasswordEncoder
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt for password encoding
    }

    @Bean // Defines a bean for DaoAuthenticationProvider
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Sets the custom UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder
        return authProvider;
    }

    @Bean // Defines a bean for SecurityFilterChain
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider()); // Sets the
        // authentication provider
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").authenticated() // Requires authentication for
                // "/users" endpoint
                .anyRequest().permitAll() // Permits all other requests
        )

                .formLogin(login -> login.loginPage("/login")
                        .usernameParameter("email") // Sets the username parameter to "email"
                        .defaultSuccessUrl("/users") // Redirects to "/users" on successful login
                        .permitAll() // Allows everyone to access the login page
                )

                .logout(logout -> logout.logoutSuccessUrl("/").permitAll() // Redirects to
                // "/" on logout and allows
                // everyone to access it
                );

        return http.build(); // Builds the SecurityFilterChain
    }
}
