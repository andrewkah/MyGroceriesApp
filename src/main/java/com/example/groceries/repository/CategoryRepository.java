package com.example.groceries.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.groceries.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>{

    Optional<Category> findCategoryByName(String name);
} 
