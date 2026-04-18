package com.example.siteecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String address;
    private List<CartItem> cart;
    
    public User() {
        this.cart = new ArrayList<>();
    }
    
    public User(String username, String email, String address) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.cart = new ArrayList<>();
    }
    
    // Getters et Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public List<CartItem> getCart() { return cart; }
    public void setCart(List<CartItem> cart) { this.cart = cart; }
    
    // Méthodes pour le panier
    public void addToCart(Product product, int quantity) {
        for (CartItem item : cart) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cart.add(new CartItem(product, quantity));
    }
    
    public void removeFromCart(Product product) {
        cart.removeIf(item -> item.getProduct().getId() == product.getId());
    }
    
    public double getCartTotal() {
        return cart.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
    
    public int getCartItemCount() {
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
    
    public void clearCart() {
        cart.clear();
    }
}
