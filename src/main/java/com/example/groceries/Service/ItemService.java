package com.example.groceries.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.groceries.Entity.Category;
import com.example.groceries.Entity.Item;
import com.example.groceries.Request.ItemRequest;
import com.example.groceries.repository.CategoryRepository;
import com.example.groceries.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public List<Item> getItems(Category category) {
        logger.info("get items method in Item Service has started");
        return itemRepository.findItemsByCategory(category.getCategoryId());
    }

    public Item addItem(String username, Item item) {
        logger.info("add items method in Item Service has started");
        Optional<Category> categories = categoryRepository.findCategoryByUserName(username);
        logger.info(categories + " added");
        if (categories.isPresent()) {
            List<Item> items = itemRepository.findItemsByCategory(item.getCategory());
            for (Item existingItem : items) {
                if (existingItem.getName().equals(item.getName())) {
                    throw new IllegalStateException("Item already exists");
                }
            }
        }
        Item savedItem = itemRepository.save(item);
        logger.info(item + " added");
        logger.info("add items method in Item Service has ended");
        return savedItem;
    }

    public Item getSingleItem(UUID itemId) {
        logger.info("add items method in Item Service has started");
        return itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException("Item not found"));
    }

    public String removeItem(@NonNull UUID itemId) {
        logger.info("remove items method in Item Service has started");
        boolean idExists = itemRepository.existsById(itemId);
        if (!idExists) {
            throw new IllegalStateException("Item not found");
        }
        itemRepository.deleteById(itemId);
        logger.info("remove items method in Item Service has ended");
        return "Item deleted successfully";
    }

    @Transactional
    public String updateItem(@NonNull UUID itemId, ItemRequest item) {
        logger.info("update items method in Item Service has started");
        Item dbItem = itemRepository.findById(itemId).orElseThrow(() -> new IllegalStateException(
                "Item with id " + itemId + " does not exist"));
        if (item.name() != null && item.name().length() > 0 && !Objects.equals(dbItem.getName(), item.name())) {
            dbItem.setName(item.name());

        }
        if (item.quantity() != null && item.quantity() > 0 && !Objects.equals(dbItem.getQuantity(), item.quantity())) {
            dbItem.setQuantity(item.quantity());
        }
        if (item.unit() != null && item.unit().length() > 0 && !Objects.equals(dbItem.getUnit(), item.unit())) {
            dbItem.setUnit(item.unit());
        }
        if (item.price() != null && item.price() > 0 && !Objects.equals(dbItem.getPrice(), item.price())) {
            dbItem.setPrice(item.price());
        }
        itemRepository.save(dbItem);
        logger.info(item.name() + " updated successfully");
        logger.info("update items method in Item Service has ended");
        return "Item updated successfully";
    }

    public List<Item> getItemsByQuantity() {
        List<Item> items = itemRepository.findItemsByQuantity(5.0);
        return items;
    }

}
