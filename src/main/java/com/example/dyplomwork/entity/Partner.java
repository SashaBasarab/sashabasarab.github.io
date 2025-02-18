package com.example.dyplomwork.entity;

import jakarta.persistence.*;

@Entity
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String discountConditions;

    @Column(nullable = false)
    private Integer discountPercentage;

    private String address;

    private String imageUrl; // Додаємо URL зображення


    public Partner() {}

    public Partner(String name, String description, String discountConditions, Integer discountPercentage, String address, String imageUrl) {
        this.name = name;
        this.description = description;
        this.discountConditions = discountConditions;
        this.discountPercentage = discountPercentage;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    // Геттери та сеттери
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountConditions() {
        return discountConditions;
    }

    public void setDiscountConditions(String discountConditions) {
        this.discountConditions = discountConditions;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
