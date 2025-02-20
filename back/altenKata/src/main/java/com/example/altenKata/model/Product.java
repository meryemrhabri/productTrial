package com.example.altenKata.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column()
    private String image;

    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private double price;
    @Column(nullable = false)
    private Long quantity;
    @Column()
    private String internalReference;
    @Column()
    private Long shellId;
    @Column()
    private String inventoryStatus;
    @Column()
    private Long rating;
    @Column()
    private String createdAt;
    @Column()
    private String updatedAt;
}
