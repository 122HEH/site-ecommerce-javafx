package com.example.siteecommerce;

import com.example.siteecommerce.model.Product;
import com.example.siteecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EcommerceController implements Initializable {
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private Button searchButton;
    @FXML private Button cartButton;
    @FXML private Button checkoutButton;
    @FXML private Label cartLabel;
    @FXML private Label totalLabel;
    @FXML private GridPane productsGrid;
    
    private DataService dataService;
    private List<Product> currentProducts;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataService = DataService.getInstance();
        
        // Initialiser les catégories
        categoryComboBox.getItems().addAll("Tous", "Vêtements", "Multimédia");
        categoryComboBox.setValue("Tous");
        
        // Charger tous les produits au démarrage
        loadAllProducts();
        
        // Mettre à jour le panier
        updateCartDisplay();
        
        // Écouter les changements de texte dans la recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.trim().isEmpty()) {
                loadAllProducts();
            }
        });
    }
    
    @FXML
    private void onSearchClicked() {
        String searchTerm = searchField.getText().trim();
        String selectedCategory = categoryComboBox.getValue();
        
        if (!searchTerm.isEmpty()) {
            currentProducts = dataService.searchProducts(searchTerm);
        } else if (!"Tous".equals(selectedCategory)) {
            currentProducts = dataService.getProductsByCategory(selectedCategory);
        } else {
            currentProducts = dataService.getAllProducts();
        }
        
        displayProducts(currentProducts);
    }
    
    private void loadAllProducts() {
        currentProducts = dataService.getAllProducts();
        displayProducts(currentProducts);
    }
    
    private void displayProducts(List<Product> products) {
        productsGrid.getChildren().clear();
        
        int col = 0;
        int row = 0;
        int maxCols = 4; // Nombre de colonnes par ligne
        
        for (Product product : products) {
            VBox productCard = createProductCard(product);
            productsGrid.add(productCard, col, row);
            
            col++;
            if (col >= maxCols) {
                col = 0;
                row++;
            }
        }
    }
    
    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        card.setPrefWidth(250);
        card.setPrefHeight(350);
        
        // Image du produit
        ImageView imageView = new ImageView();
        try {
            Image image = product.getImage();
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(150);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            imageView.setStyle("-fx-background-color: #bdc3c7; -fx-min-width: 200; -fx-min-height: 150;");
        }
        
        // Nom du produit
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(200);
        
        // Description
        Label descLabel = new Label(product.getDescription());
        descLabel.setFont(Font.font("Arial", 11));
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(200);
        descLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        // Prix
        Label priceLabel = new Label(String.format("%.2f€", product.getPrice()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        priceLabel.setStyle("-fx-text-fill: #27ae60;");
        
        // Stock
        Label stockLabel = new Label("Stock: " + product.getStock());
        stockLabel.setFont(Font.font("Arial", 10));
        stockLabel.setStyle("-fx-text-fill: #e74c3c;");
        
        // Bouton Ajouter au panier
        Button addButton = new Button("🛒 Ajouter au panier");
        addButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 5;");
        addButton.setOnAction(e -> addToCart(product));
        
        // Conteneur pour le prix et le stock
        HBox priceStockBox = new HBox(10);
        priceStockBox.getChildren().addAll(priceLabel, stockLabel);
        
        card.getChildren().addAll(imageView, nameLabel, descLabel, priceStockBox, addButton);
        card.setAlignment(javafx.geometry.Pos.CENTER);
        
        return card;
    }
    
    private void addToCart(Product product) {
        // Demander la quantité
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Ajouter au panier");
        dialog.setHeaderText("Quantité pour " + product.getName());
        dialog.setContentText("Quantité:");
        
        dialog.showAndWait().ifPresent(quantityStr -> {
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity > 0 && quantity <= product.getStock()) {
                    dataService.getCurrentUser().addToCart(product, quantity);
                    updateCartDisplay();
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Produit ajouté");
                    alert.setHeaderText(null);
                    alert.setContentText(quantity + " x " + product.getName() + " ajouté(s) au panier !");
                    alert.showAndWait();
                } else {
                    showError("Quantité invalide ou stock insuffisant !");
                }
            } catch (NumberFormatException e) {
                showError("Veuillez entrer un nombre valide !");
            }
        });
    }
    
    @FXML
    private void onCartClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("cart-view.fxml"));
            Stage cartStage = new Stage();
            cartStage.setTitle("Mon Panier");
            cartStage.setScene(new javafx.scene.Scene(loader.load(), 800, 600));
            cartStage.show();
            
            // Fermer la fenêtre principale
            ((Stage) cartButton.getScene().getWindow()).close();
        } catch (IOException e) {
            showError("Erreur lors de l'ouverture du panier : " + e.getMessage());
        }
    }
    
    @FXML
    private void onCheckoutClicked() {
        if (dataService.getCurrentUser().getCart().isEmpty()) {
            showError("Votre panier est vide !");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de commande");
        alert.setHeaderText("Confirmer votre commande ?");
        alert.setContentText("Total : " + String.format("%.2f€", dataService.getCurrentUser().getCartTotal()));
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dataService.processOrder();
                updateCartDisplay();
                
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Commande confirmée");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Votre commande a été passée avec succès !");
                successAlert.showAndWait();
            }
        });
    }
    
    private void updateCartDisplay() {
        int itemCount = dataService.getCurrentUser().getCartItemCount();
        double total = dataService.getCurrentUser().getCartTotal();
        
        cartLabel.setText("Panier (" + itemCount + ")");
        totalLabel.setText("Total: " + String.format("%.2f€", total));
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
