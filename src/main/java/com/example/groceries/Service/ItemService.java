package com.example.groceries.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.example.groceries.Entity.Category;
import com.example.groceries.Entity.Item;
import com.example.groceries.Request.ItemRequest;
import com.example.groceries.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

    Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;

    public List<Item> getItems(Category category) {
        logger.info("get items method in Item Service has started");
        return itemRepository.findItemsByCategory(category.getCategoryId());
    }

    public Item addItem(Item item) {
        logger.info("add items method in Item Service has started");
        Optional<Item> itemByName = itemRepository.findItemByName(item.getName());
        if (itemByName.isPresent()) {
            throw new IllegalStateException("Item already exists");
        } else {
            Item savedItem = itemRepository.save(item);
            logger.info(item + " added");
            logger.info("add items method in Item Service has ended");
            return savedItem;
        }

    }

    @SuppressWarnings("null")
    public Item getSingleItem(UUID itemId){
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

    @SuppressWarnings("null")
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

}
