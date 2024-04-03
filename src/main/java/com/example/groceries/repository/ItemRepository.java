package com.example.groceries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.groceries.Entity.Item;

import java.util.*;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    Optional<Item> findItemByName(String name);

    @Query("SELECT i FROM Item i WHERE i.category = :categoryId")
    List<Item> findItemsByCategory(@Param("categoryId") UUID category);

    @Query("SELECT i FROM Item i WHERE i.quantity < :quantityThreshold")
    List<Item> findItemsByQuantity(Double quantityThreshold);

}
