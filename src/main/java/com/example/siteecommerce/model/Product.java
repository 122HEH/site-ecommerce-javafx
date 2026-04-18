package com.example.siteecommerce.model;

import javafx.scene.image.Image;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String imagePath;
    private String category;
    private int stock;
    
    public Product() {}
    
    public Product(int id, String name, String description, double price, String imagePath, String category, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.category = category;
        this.stock = stock;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    // Méthode pour obtenir l'image JavaFX
    public Image getImage() {
        try {
            return new Image(getClass().getResourceAsStream(imagePath));
        } catch (Exception e) {
            // Image par défaut si l'image n'est pas trouvée
            return new Image(getClass().getResourceAsStream("/images/default-product.png"));
        }
    }
    
    @Override
    public String toString() {
        return name + " - " + price + "€";
    }
}
