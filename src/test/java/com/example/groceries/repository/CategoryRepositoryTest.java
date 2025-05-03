package com.example.groceries.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.groceries.Entity.Category;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryTest.class);

    @Test
    void testFindCategoryByName() {
        // Given
        Category category = new Category();
        category.setName("Test Category");
        categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findCategoryByName("Test Category");

        // Then
        // if (foundCategory.isPresent()) {
        //     logger.info("✅ Found category: ", foundCategory.get().getName());
        // } else {
        //     logger.info("❌ Category not found!");
        // }
        assertNotNull(foundCategory);
        assertEquals("Test Category", foundCategory.get().getName());
    }

    @Test
    void testFindCategoryByNameAndUsername() {
        String name = "Vegetables";
        String username = "Fred";
        Category category = new Category();
        category.setUsername(username);
        category.setName(name);
        categoryRepository.save(category);

        Optional<Category> foundNameAndUsername = categoryRepository.findCategoryByNameAndUsername(name, username);
        // if (foundNameAndUsername.isPresent()) {
        //     logger.info("✅ Found Category: ", foundNameAndUsername.get().getName());
        //     logger.info("✅ Found User name: ", foundNameAndUsername.get().getUsername());
        // } else {
        //     logger.info("❌ No User name or Category found!");
        // }
        assertNotNull(foundNameAndUsername);
        assertEquals(name, foundNameAndUsername.get().getName());
        assertEquals(username, foundNameAndUsername.get().getUsername());
    }

}
