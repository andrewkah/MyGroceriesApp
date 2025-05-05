package com.example.groceries.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.groceries.Entity.Category;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindCategoryByName() {
        // Given
        Category category = new Category();
        category.setName("Test Category");
        categoryRepository.save(category);

        // When
        Optional<Category> foundCategory = categoryRepository.findCategoryByName("Test Category");

        // Then
        assertNotNull(foundCategory);
        // assertEquals("Test Category", foundCategory.get().getName());
        assertThat(foundCategory.get().getName()).isEqualTo("Test Category")
                .as("The category name matches the expected value: 'Test Category'");
        System.out.println("✅ Test category found successfully!");
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
        assertNotNull(foundNameAndUsername);
        // assertEquals(name, foundNameAndUsername.get().getName());
        assertThat(foundNameAndUsername.get().getName()).isEqualTo(name)
                .as("The category name matches the expected value: 'Vegetables'");
        System.out.println("✅ Test category found successfully!");
        // assertEquals(username, foundNameAndUsername.get().getUsername());
        assertThat(foundNameAndUsername.get().getUsername()).isEqualTo(
                username)
                .as("The user name matches the expected value: 'Fred'");
        System.out.println("✅ Test user name found successfully! ");
    }

}
