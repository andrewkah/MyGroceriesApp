package com.example.groceries.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groceries.Entity.Category;
import com.example.groceries.Entity.Item;
import com.example.groceries.Request.CategoryRequest;
import com.example.groceries.repository.CategoryRepository;
import com.example.groceries.repository.ItemRepository;
import com.example.groceries.security.entity.User;
import com.example.groceries.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;
	private final ItemService itemService;
	private final UserRepository userRepository;

	public Category addCategory(Category category) {
		logger.info("add category method in Category Service has started");
		Optional<User> currentUser = userRepository.findUserByUsername(category.getUsername());
		if (!currentUser.isPresent()) {
			throw new IllegalStateException("User not found");
		} else {
			Optional<Category> existingCategory = categoryRepository.findCategoryByNameAndUsername(category.getName(),
					category.getUsername());
			if (existingCategory.isPresent()) {
				throw new IllegalStateException("Category already exists");
			}

			Category categoryOut = categoryRepository.save(category);
			logger.info(category + " created");
			logger.info("add category method in Category Service has ended");
			return categoryOut;
		}

	}

	public List<Category> getCategories(String username) {
		logger.info("get category method in Category Service has started");
		Optional<User> currentUser = userRepository.findUserByUsername(username);
		if (!currentUser.isPresent()) {
			throw new IllegalStateException("User not found");
		} else {
			Optional<Category> categoryIn = categoryRepository.findCategoryByUsername(username);
			if (!categoryIn.isPresent()) {
				throw new IllegalStateException("Category not found");
			}
			return categoryRepository.findAll();
		}
	}

	public String deleteCategory(UUID categoryId, String username) {
		logger.info("delete category method in Category Service has started");
		Optional<User> currentUser = userRepository.findUserByUsername(username);
		if (!currentUser.isPresent()) {
			throw new IllegalStateException("User not found");
		} else {
			boolean idExists = categoryRepository.existsById(categoryId);
			if (!idExists && currentUser.isPresent()) {
				throw new IllegalStateException("Category does not exist!");
			} else {
				List<Item> items = itemRepository.findItemsByCategory(categoryId);
				for (Item itemIn : items) {
					itemService.removeItem(itemIn.getItemId());
				}
				categoryRepository.deleteById(categoryId);
				logger.info("delete category method in Category Service has ended");
				return "Category deleted successfully";
			}
		}
	}

	@Transactional
	public String updateCategory(@NonNull UUID categoryId, CategoryRequest category) {
		logger.info("update category method in Category Service has started");
		Optional<User> currentUser = userRepository.findUserByUsername(category.username());
		if (!currentUser.isPresent()) {
			throw new IllegalStateException("User not found");
		} else {
			Category dbCategory = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new IllegalStateException(
							"Category does not exist!"));

			if (category.name() != null && category.name().length() > 0
					&& !Objects.equals(dbCategory.getName(), category.name())) {
				dbCategory.setName(category.name());
			}
			logger.info("update category method in Category Service has ended");
			return "Category updated successfully";
		}
	}

}
