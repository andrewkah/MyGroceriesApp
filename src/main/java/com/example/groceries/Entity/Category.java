package com.example.groceries.Entity;

import java.util.*;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    @Id
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Column(name = "category_id", nullable = false, unique = true, updatable = false)
    protected UUID categoryId;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_id")
    private String username;

}
