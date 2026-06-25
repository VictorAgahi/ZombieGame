---
name: Senior Architecture Module Generator
description: Standardise la création de nouveaux modules métier en respectant strictement la Clean Architecture (Ports & Adapters) du projet Run or Die.
---

# 🏗️ Architecture Module Generator - Niveau Sénior

Cette compétence définit les règles incontournables et l'architecture exacte à utiliser pour la génération de tout nouveau module dans le projet. L'objectif est de garantir l'homogénéité avec les modules de référence (`user` et `edition`).

## 1. Structure Obligatoire en Couches (Ports & Adapters)
Chaque nouveau module doit respecter cette structure de packages stricte :

```text
[nom-du-module]/src/main/java/fr/epita/zombie/[nom-du-module]/
├── application/           # Adaptateurs d'Entrée (Primary Adapters)
│   ├── controllers/       # Points d'entrée REST (Endpoints)
│   ├── dtos/              # Objets de transferts isolés (requests/, responses/)
│   ├── exceptions/        # Gestion globale (GlobalExceptionHandler)
│   └── mappers/           # Map de DTO -> Entité Métier -> DTO
├── domain/                # Le Cœur Métier (Agnostique du framework)
│   ├── entities/          # Entités métiers (Immuables)
│   ├── exceptions/        # Exceptions purement métiers (Business errors)
│   ├── ports/             # Interfaces (Contrats pour l'infrastructure)
│   ├── services/          # Logique et règles métiers pures
│   └── valueobjects/      # Objets valeurs (ex: Role, Status)
└── infrastructure/        # Adaptateurs de Sortie (Secondary Adapters)
    ├── config/            # Configurations Spring spécifiques
    ├── mappers/           # Map de Entité Métier -> Model JPA
    ├── models/            # Modèles JPA (Base de données, annotations @Entity)
    ├── repositories/      # Interfaces JPA et Adaptateurs (implémentations des Ports)
    └── services/          # Services externes (API tiers, Event Brokers)
```

## 2. Règle d'Or de l'Isolation du Domaine
Le package `domain/` est le cœur de l'application. 
**IL EST STRICTEMENT INTERDIT** d'y trouver les imports suivants :
- `org.springframework.*`
- `jakarta.*` (sauf annotations spécifiques validées si aucune alternative Java)
- `fr.epita.zombie.*.infrastructure.*`
- `fr.epita.zombie.*.application.*`
Le domaine ne doit dépendre que de lui-même et des librairies standards Java (ou Lombok).

## 3. Normes de Codage et de Conception
- **Préfixe des Interfaces** : Absolument toutes les interfaces doivent commencer par la lettre majuscule `I` (ex: `IUserRepository`, `IEncryptionService`, `IJpaUserRepository`).
- **Immuabilité des Entités** : Les entités du domaine (`domain/entities`) doivent être immuables. Utilisez des attributs `private final` et l'annotation `@Builder(toBuilder = true)` de Lombok pour la mise à jour (pas de "setters").
- **DRY & Exceptions** : Les controllers ne font pas de "try-catch". Laissez les exceptions du domaine se propager jusqu'au `GlobalExceptionHandler` qui les convertira en `ErrorResponse` standardisée.
- **Zéro Javadoc** : Les commentaires `/**` sont interdits. Le nommage des méthodes, des variables et des classes doit être suffisamment explicite.

## 4. Documentation API (Swagger / OpenAPI)
Tous les contrôleurs HTTP dans `application/controllers/` doivent être couverts :
- `@Tag` sur la classe du contrôleur.
- `@Operation` sur chaque méthode.
- `@ApiResponses` détaillant les codes de retours standards (200/201, 400, 404, 500) avec les schémas correspondants (ex: `ErrorResponse.class`).

## 5. Principes de Tests (Rappels des autres Skills)
- Pour toute nouvelle création de classe de production, générer sa classe de test correspondante.
- Séparer le code en `*Test.java` (Unitaires, sans Spring) et `*IT.java` (Intégration, avec Spring, `MockMvc`).
- Utiliser systématiquement les Patterns "Test Data Factory" ou "Builder" pour ne jamais instancier des objets métiers avec de lourds `new` dans le corps des tests.
