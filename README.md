# E-Commerce Store - Application Desktop JavaFX

## Description
Application e-commerce desktop développée avec JavaFX, basée sur une architecture MVC :
- **FXML** pour les interfaces utilisateur
- **Contrôleurs** pour la logique applicative
- **Modèles** pour les données
- **Services** pour la gestion des données (stockage en mémoire)

## Fonctionnalités

### Interface principale
- Catalogue de produits avec images
- Recherche par nom et catégorie
- Affichage des détails produits (nom, description, prix, stock)
- Interface moderne et responsive

### Panier
- Ajout et suppression de produits
- Modification des quantités
- Calcul automatique du total
- Écran dédié à la gestion du panier

### Gestion des produits
- 8 produits préconfigurés avec images
- Catégorisation (Vêtements, Multimédia)
- Gestion du stock
- Images intégrées automatiquement

## Structure du projet

```
src/main/java/com/example/siteecommerce/
├── HelloApplication.java          # Point d'entrée principal
├── EcommerceController.java       # Controller de la page principale
├── CartController.java           # Controller du panier
├── model/
│   ├── Product.java              # Modèle produit
│   ├── CartItem.java             # Modèle article panier
│   └── User.java                 # Modèle utilisateur
└── service/
    └── DataService.java          # Service de gestion des données
```

```
src/main/resources/com/example/siteecommerce/
├── ecommerce-view.fxml           # Interface principale
└── cart-view.fxml                # Interface du panier

src/main/resources/images/
└── [Vos 8 images de produits]    # Images des produits
```

## Installation et lancement

### Prérequis
- Java 17 ou supérieur
- JavaFX 17 (optionnel, le script s'en charge)

### Lancement Rapide
1. Double-cliquez sur `run-app.bat`
2. L'application se lance automatiquement.

### Lancement Manuel
Si vous avez JavaFX installé :
```bash
java --module-path "C:\Program Files\Java\javafx-17.0.2\lib" --add-modules javafx.controls,javafx.fxml -cp "target/classes" com.example.siteecommerce.HelloApplication
```

## Utilisation

### Navigation
1. **Page principale** : consultez le catalogue, recherchez et ajoutez au panier
2. **Panier** : cliquez sur "Panier" pour gérer vos achats
3. **Commande** : cliquez sur "Commander" pour finaliser

### Fonctionnalités Clés
- **Recherche** : Tapez dans la barre de recherche ou sélectionnez une catégorie
- **Ajout au panier** : Cliquez sur "Ajouter au panier" sur chaque produit
- **Gestion du panier** : Modifiez les quantités ou supprimez des articles
- **Commande** : Validez votre commande avec le bouton "Passer commande"

## Interface

### Design Moderne
- Couleurs professionnelles (bleu, vert, rouge)
- Cartes produits avec ombres
- Interface responsive

### Responsive
- Grille adaptative (4 colonnes)
- Scroll automatique
- Fenêtre redimensionnable

## Données

### Produits Inclus
1. T-shirt Casual Confort - 25.99€
2. T-shirt Cotton Professionnel - 29.99€
3. T-shirt Vintage - 22.99€
4. Veste DJO - 89.99€
5. Sweat Roxy - 45.99€
6. Collection Images - 19.99€
7. Style Moderne - 34.99€
8. Design Téléchargement - 14.99€

### Stockage
- Données en mémoire (pas de base de données)
- Persistance pendant la session
- Utilisateur par défaut configuré

## Personnalisation

### Ajouter des Produits
Modifiez `DataService.java` dans la méthode `initializeProducts()`.

### Modifier l'Interface
Éditez les fichiers `.fxml` dans `src/main/resources/`.

### Changer les Images
Remplacez les fichiers dans `src/main/resources/images/` et mettez à jour les chemins dans `DataService.java`.

## Notes techniques

- **Architecture MVC** respectée
- **JavaFX 17** pour l'interface
- **FXML** pour la séparation vue/contrôleur
- **Singleton** pour le service de données
- **Observable Pattern** pour les mises à jour

## Résultat

Une application e-commerce desktop complète, moderne et fonctionnelle, adaptée pour apprendre JavaFX et l'architecture MVC.

---
