package com.example.siteecommerce;

import com.example.siteecommerce.model.CartItem;
import com.example.siteecommerce.model.Product;
import com.example.siteecommerce.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    
    @FXML private Button backButton;
    @FXML private Label cartTotalLabel;
    @FXML private Label finalTotalLabel;
    @FXML private Button clearCartButton;
    @FXML private Button checkoutButton;
    @FXML private VBox cartItemsContainer;
    
    private DataService dataService;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataService = DataService.getInstance();
        updateCartDisplay();
    }
    
    @FXML
    private void onBackClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ecommerce-view.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("E-Commerce Store");
            mainStage.setScene(new javafx.scene.Scene(loader.load(), 1200, 800));
            mainStage.show();
            
            // Fermer la fenêtre du panier
            ((Stage) backButton.getScene().getWindow()).close();
        } catch (IOException e) {
            showError("Erreur lors du retour : " + e.getMessage());
        }
    }
    
    @FXML
    private void onClearCartClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Vider le panier");
        alert.setHeaderText("Êtes-vous sûr de vouloir vider votre panier ?");
        alert.setContentText("Cette action ne peut pas être annulée.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dataService.getCurrentUser().clearCart();
                updateCartDisplay();
            }
        });
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
                successAlert.setContentText("Votre commande a été passée avec succès ! Merci pour votre achat.");
                successAlert.showAndWait();
                
                // Retourner à la page principale
                onBackClicked();
            }
        });
    }
    
    private void updateCartDisplay() {
        cartItemsContainer.getChildren().clear();
        List<CartItem> cartItems = dataService.getCurrentUser().getCart();
        
        if (cartItems.isEmpty()) {
            Label emptyLabel = new Label("Votre panier est vide");
            emptyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d;");
            cartItemsContainer.getChildren().add(emptyLabel);
        } else {
            for (CartItem item : cartItems) {
                VBox itemCard = createCartItemCard(item);
                cartItemsContainer.getChildren().add(itemCard);
            }
        }
        
        // Mettre à jour les totaux
        double total = dataService.getCurrentUser().getCartTotal();
        cartTotalLabel.setText("Total: " + String.format("%.2f€", total));
        finalTotalLabel.setText(String.format("%.2f€", total));
    }
    
    private VBox createCartItemCard(CartItem cartItem) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        
        Product product = cartItem.getProduct();
        
        HBox itemHBox = new HBox(15);
        itemHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        // Image du produit
        ImageView imageView = new ImageView();
        try {
            imageView.setImage(product.getImage());
            imageView.setFitWidth(80);
            imageView.setFitHeight(80);
            imageView.setPreserveRatio(true);
        } catch (Exception e) {
            imageView.setStyle("-fx-background-color: #bdc3c7; -fx-min-width: 80; -fx-min-height: 80;");
        }
        
        // Informations du produit
        VBox productInfo = new VBox(5);
        
        Label nameLabel = new Label(product.getName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Label descLabel = new Label(product.getDescription());
        descLabel.setFont(Font.font("Arial", 11));
        descLabel.setStyle("-fx-text-fill: #7f8c8d;");
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(300);
        
        Label priceLabel = new Label(String.format("%.2f€", product.getPrice()));
        priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        priceLabel.setStyle("-fx-text-fill: #27ae60;");
        
        productInfo.getChildren().addAll(nameLabel, descLabel, priceLabel);
        
        // Contrôles de quantité
        HBox quantityControls = new HBox(10);
        quantityControls.setAlignment(javafx.geometry.Pos.CENTER);
        
        Button decreaseButton = new Button("-");
        decreaseButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 3;");
        decreaseButton.setPrefWidth(30);
        decreaseButton.setOnAction(e -> updateQuantity(cartItem, -1));
        
        Label quantityLabel = new Label(String.valueOf(cartItem.getQuantity()));
        quantityLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        quantityLabel.setPrefWidth(40);
        quantityLabel.setAlignment(javafx.geometry.Pos.CENTER);
        
        Button increaseButton = new Button("+");
        increaseButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 3;");
        increaseButton.setPrefWidth(30);
        increaseButton.setOnAction(e -> updateQuantity(cartItem, 1));
        
        quantityControls.getChildren().addAll(decreaseButton, quantityLabel, increaseButton);
        
        // Total et bouton supprimer
        HBox totalAndRemove = new HBox(20);
        totalAndRemove.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        
        Label totalLabel = new Label(String.format("%.2f€", cartItem.getTotalPrice()));
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        totalLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        Button removeButton = new Button("🗑️ Supprimer");
        removeButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 5;");
        removeButton.setOnAction(e -> removeFromCart(product));
        
        totalAndRemove.getChildren().addAll(totalLabel, removeButton);
        
        itemHBox.getChildren().addAll(imageView, productInfo);
        
        HBox bottomHBox = new HBox();
        bottomHBox.getChildren().addAll(quantityControls, new javafx.scene.layout.Region(), totalAndRemove);
        HBox.setHgrow(bottomHBox.getChildren().get(1), javafx.scene.layout.Priority.ALWAYS);
        
        card.getChildren().addAll(itemHBox, bottomHBox);
        
        return card;
    }
    
    private void updateQuantity(CartItem cartItem, int change) {
        int newQuantity = cartItem.getQuantity() + change;
        
        if (newQuantity <= 0) {
            removeFromCart(cartItem.getProduct());
        } else if (newQuantity <= cartItem.getProduct().getStock()) {
            cartItem.setQuantity(newQuantity);
            updateCartDisplay();
        } else {
            showError("Quantité supérieure au stock disponible !");
        }
    }
    
    private void removeFromCart(Product product) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer du panier");
        alert.setHeaderText("Supprimer " + product.getName() + " du panier ?");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                dataService.getCurrentUser().removeFromCart(product);
                updateCartDisplay();
            }
        });
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
