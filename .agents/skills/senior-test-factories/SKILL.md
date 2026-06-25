---
name: Test Data Factories
description: Modèles de conception pour générer des données de test propres, réutilisables et maintenables (Pattern Builder / Object Mother).
---

# Test Data Factories & Object Mothers

Pour garantir la lisibilité et la maintenabilité, la création de données de test complexes doit être centralisée et standardisée.

## 1. Bannir les instanciations "en dur"
- Interdiction d'instancier des objets métiers complexes directement dans le bloc `Arrange` des méthodes de test avec de nombreux `new` imbriqués ou des setters à rallonge.
- Cela pollue visuellement le test et rend les modifications de la structure des objets extrêmement coûteuses.

## 2. Pattern Test Data Builder (Fluent API)
- Implémenter des classes `Factory` ou `Builder` dédiées aux tests.
- Placer ces classes dans des fichiers séparés au sein d'un package partagé du module de test (ex: `fr.epita.zombie.user.factories`).
- Ces builders doivent initialiser les objets avec des données **valides par défaut** pour éviter l'échec des validations de base.

**Exemple d'approche recommandée :**
```java
// Dans UserTestFactory.java
User user = UserTestFactory.aValidUser()
    .withEmail("specific@test.com") // Surcharge uniquement la donnée pertinente pour le test
    .build();
```

## 3. Pattern Object Mother
- Complémentaire aux builders, ce pattern permet de définir des instances typiques pré-configurées sous forme de méthodes statiques.
- Exemple: `UserTestFactory.createAdminUser()`, `EditionTestFactory.createPastEdition()`.

## 4. Cohérence et Types Stricts
- Les générateurs de données de test doivent respecter un **typage strict**.
- Si des identifiants (IDs) doivent être générés, utilisez des séquences ou des UUID fixes pour les tests si besoin d'assertion précise, mais évitez les valeurs `null` non contrôlées.
