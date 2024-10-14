package com.example.backend.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

 
/**
 * CustomUserDetailsService is an implementation of the UserDetailsService interface
 * that provides custom user authentication logic.
 * 
 * This service is responsible for loading user-specific data during the authentication process.
 * It uses the UserRepository to fetch user details from the database based on the provided username.
 * 
 * The class is annotated with @Autowired to inject the UserRepository dependency.
 * 
 * Methods:
 * - loadUserByUsername(String username): This method is overridden from the UserDetailsService interface.
 *   It takes a username as input and attempts to find the corresponding user in the database.
 *   If the user is not found, it throws a UsernameNotFoundException.
 *   If the user is found, it returns a CustomUserDetails object containing the user's details.
 * 
 * Dependencies:
 * - UserRepository: A repository interface for accessing user data from the database.
 * - CustomUserDetails: A custom implementation of the UserDetails interface that wraps the user entity.
 * 
 * Exceptions:
 * - UsernameNotFoundException: Thrown when a user with the specified username is not found in the database.
 */
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //representa o usuário autenticado
        return new CustomUserDetails(user);
    }
 
}
