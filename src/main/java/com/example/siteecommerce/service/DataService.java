package com.example.siteecommerce.service;

import com.example.siteecommerce.model.Product;
import com.example.siteecommerce.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataService {
    private static DataService instance;
    private List<Product> products;
    private User currentUser;
    
    private DataService() {
        initializeProducts();
        currentUser = new User("Client", "client@email.com", "123 Rue du Commerce, Paris");
    }
    
    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }
    
    private void initializeProducts() {
        products = new ArrayList<>();
        
        // Ajout des produits avec les images existantes
        products.add(new Product(1, "T-shirt Casual Confort", 
            "T-shirt en coton bio, confortable pour tous les jours", 
            25.99, "/images/tshirt casual confort(3).jpeg", "Vêtements", 15));
            
        products.add(new Product(2, "T-shirt Cotton Professionnel", 
            "T-shirt professionnel en coton de qualité supérieure", 
            29.99, "/images/tshirt cotton proffesionnal(3) - Copie.jpeg", "Vêtements", 12));
            
        products.add(new Product(3, "T-shirt Vintage", 
            "Style vintage rétro pour un look unique", 
            22.99, "/images/tshirt vintage.jpeg", "Vêtements", 8));
            
        products.add(new Product(4, "Veste DJO", 
            "Veste moderne et élégante pour toutes occasions", 
            89.99, "/images/djo - Copie.jpg", "Vêtements", 5));
            
        products.add(new Product(5, "Sweat Roxy", 
            "Sweat confortable avec style sportif", 
            45.99, "/images/erjkt04045_roxy,w_nds0_frt1 - Copie (2).jpg", "Vêtements", 10));
            
        products.add(new Product(6, "Collection Images", 
            "Pack d'images premium pour vos projets", 
            19.99, "/images/images - Copie.jpeg", "Multimédia", 20));
            
        products.add(new Product(7, "Style Moderne", 
            "Article tendance avec design contemporain", 
            34.99, "/images/mmm.jpeg", "Vêtements", 7));
            
        products.add(new Product(8, "Design Téléchargement", 
            "Contenu digital premium", 
            14.99, "/images/téléchargement (2).jpeg", "Multimédia", 25));
    }
    
    // Méthodes pour les produits
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    public List<Product> getProductsByCategory(String category) {
        return products.stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
    
    public List<String> getCategories() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
    
    public Product getProductById(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    public List<Product> searchProducts(String searchTerm) {
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                           p.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    // Méthodes pour l'utilisateur
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void updateUser(User user) {
        this.currentUser = user;
    }
    
    // Méthode pour passer commande
    public void processOrder() {
        // Simulation de traitement de commande
        currentUser.clearCart();
    }
}
