package com.example.groceries.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groceries.Entity.Category;
import com.example.groceries.Entity.Item;
import com.example.groceries.Request.ItemRequest;
import com.example.groceries.Service.ItemService;
import com.example.groceries.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/v1/category/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final CategoryRepository categoryRepository;


    @PostMapping(path = "/add/{username}")
    public ResponseEntity<?> addItem(@PathVariable("username") String username, @RequestBody Item item){
        try {
            Item savedItem = itemService.addItem(username, item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(path = "/{categoryId}")
    public List<Item> getItems(@PathVariable("categoryId") UUID categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        return itemService.getItems(category);
    }

    @GetMapping(path = "/quantity")
    public ResponseEntity<?> getItemsByQuantity(){
        try {
            List<Item> item = itemService.getItemsByQuantity();
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path="/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable("itemId") @NonNull UUID itemId,
        @RequestBody ItemRequest item){
            try {
                String updatedItem = itemService.updateItem(itemId, item);
                return ResponseEntity.status(HttpStatus.CREATED).body(updatedItem);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

    @DeleteMapping(path = "/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") @NonNull UUID itemId){
        try {
            String savedItem = itemService.removeItem(itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
