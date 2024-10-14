/**
 * UserRepository is an interface that extends JpaRepository.
 * It provides CRUD operations for the Users entity.
 * 
 * JpaRepository is a JPA (Java Persistence API) specific extension of Repository.
 * It contains the full API of CrudRepository and PagingAndSortingRepository.
 * 
 * @param <User> the entity type the repository manages
 * @param <Long> the type of the entity's identifier
 */

package com.example.backend.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find a user by their email address
    /*@Query Annotation: This annotation is used to define a custom query.
    It can be placed on repository methods to specify the JPQL or SQL query to be executed.

    JPQL Query: "SELECT u FROM User u WHERE u.email = ?1" is a JPQL query.
    
    SELECT u: This part specifies that the query should select the User entity u.
    
    FROM User u: This part specifies the entity to query from, which is the User entity.
    The alias u is used to refer to the User entity in the query.
    
    WHERE u.email = ?1: This part specifies the condition for the query.
    It filters the results to include only those User entities where the email field matches the provided parameter.
    The ?1 is a placeholder for the first method parameter. */
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email); // Method to find a user by email
}
