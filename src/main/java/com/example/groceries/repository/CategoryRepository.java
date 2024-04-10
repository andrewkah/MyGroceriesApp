package com.example.groceries.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.groceries.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{

    Optional<Category> findCategoryByName(String name);

    @Query("SELECT i FROM Category i WHERE i.username = :usernameStr")
    Optional<Category> findCategoryByUsername(String usernameStr);

    @Query("SELECT c FROM Category c WHERE c.username = :username AND c.name = :name")
    Optional<Category> findCategoryByNameAndUsername(@Param("name") String name, @Param("username") String username);

} 
