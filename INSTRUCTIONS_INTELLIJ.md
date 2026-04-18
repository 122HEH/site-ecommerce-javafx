# 🚀 Instructions pour lancer l'application dans IntelliJ IDEA

## 📋 Étapes à suivre dans IntelliJ IDEA

### 1. Ouvrir le projet
- Ouvrez IntelliJ IDEA
- File → Open
- Sélectionnez le dossier `site-Ecommerce`
- Cliquez sur "Open as Project"

### 2. Configurer JavaFX (si nécessaire)
- File → Project Structure → Libraries
- Cliquez sur "+" → From Maven
- Ajoutez : `org.openjfx:javafx-controls:17.0.2`
- Ajoutez : `org.openjfx:javafx-fxml:17.0.2`

### 3. Créer une configuration de run
- Run → Edit Configurations
- Cliquez sur "+" → Application
- Nom : "E-Commerce Store"
- Main class : `com.example.siteecommerce.HelloApplication`
- Module : `site-Ecommerce`
- Working directory : `C:\Users\SYB\project\site-Ecommerce`

### 4. Ajouter les VM options JavaFX
Dans la configuration créée, dans "VM options", ajoutez :
```
--module-path "C:\Program Files\Java\javafx-17.0.2\lib" --add-modules javafx.controls,javafx.fxml
```

**OU** si JavaFX n'est pas installé, utilisez cette alternative :
```
--add-modules javafx.controls,javafx.fxml
```

### 5. Lancer l'application
- Cliquez sur le bouton "Run" (▶️) à côté de la configuration
- OU utilisez le raccourci Shift+F10

## 🎯 Résultat attendu

L'application va s'ouvrir avec :
- Une fenêtre de 1200x800 pixels
- Titre : "🛍️ E-Commerce Store - Application Desktop"
- Interface moderne avec :
  - Barre de recherche en haut
  - Grille de produits (4 colonnes)
  - Vos 8 images de produits intégrées
  - Bouton panier avec compteur
  - Bouton commander

## 🔧 Si vous avez des erreurs

### Erreur "Module not found"
1. Téléchargez JavaFX depuis : https://openjfx.io/
2. Extrayez dans `C:\Program Files\Java\javafx-17.0.2\`
3. Ou modifiez le chemin dans les VM options

### Erreur "Class not found"
- Assurez-vous que `src/main/resources` est marqué comme "Resources Root"
- Clic droit sur `src/main/resources` → Mark Directory as → Resources Root

### Problème avec les images
- Vérifiez que le dossier `src/main/resources/images` contient vos 8 images
- Les images doivent avoir les noms exacts utilisés dans `DataService.java`

## 🎉 Fonctionnalités à tester

1. **Navigation** : Parcourez les produits
2. **Recherche** : Tapez dans la barre de recherche
3. **Catégories** : Sélectionnez une catégorie dans le dropdown
4. **Ajout panier** : Cliquez sur "🛒 Ajouter au panier"
5. **Gestion panier** : Cliquez sur "🛒 Panier"
6. **Commande** : Testez le processus de commande

---

**L'application est maintenant prête ! Lancez-la dans IntelliJ IDEA et prenez une capture d'écran pour me montrer le résultat !** 📸
