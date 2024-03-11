package com.example.groceries.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.groceries.Entity.Category;
import com.example.groceries.Entity.Item;
import com.example.groceries.Request.CategoryRequest;
import com.example.groceries.repository.CategoryRepository;
import com.example.groceries.repository.ItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
	Logger logger = LoggerFactory.getLogger(CategoryService.class);

	private final CategoryRepository categoryRepository;
	private final ItemRepository itemRepository;
	private final ItemService itemService;

	public CategoryService(CategoryRepository categoryRepository, ItemRepository itemRepository, ItemService itemService) {
		this.categoryRepository = categoryRepository;
		this.itemRepository = itemRepository;
		this.itemService = itemService;
	}

	public Category addCategory(Category category) {
		logger.info("add category method in Category Service has started");
		Optional<Category> categoryByName = categoryRepository.findCategoryByName(category.getName());
		if (categoryByName.isPresent()) {
			throw new IllegalStateException("Category already exists");
		} else {
			Category categoryOut = categoryRepository.save(category);
			// Category categoryIn = new Category(category.getName());

			// List<Item> items = new ArrayList<>();
			// for (Item itemIn : category.getItems()) {

			// Item item = new Item(itemIn.getPrice(), itemIn.getQuantity(),
			// itemIn.getUnit(), itemIn.getName());

			// item.setCategory(categoryIn);

			// items.add(item);
			// itemRepository.save(item);
			// }

			// categoryIn.setItems(items);

			// Category categoryOut = categoryRepository.save(categoryIn);
			logger.info(category + " created");
			logger.info("add category method in Category Service has ended");
			return categoryOut;
		}
	}

	public List<Category> getCategories() {
		logger.info("get category method in Category Service has started");
		return categoryRepository.findAll();
	}

	@SuppressWarnings("null")
	public String deleteCategory(@NonNull UUID categoryId) {
		logger.info("delete category method in Category Service has started");
		boolean idExists = categoryRepository.existsById(categoryId); 
		if (!idExists) {
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

	@Transactional
	public String updateCategory(@NonNull UUID categoryId, CategoryRequest category) {
		logger.info("update category method in Category Service has started");
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
