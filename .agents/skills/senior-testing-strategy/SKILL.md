---
name: Senior Testing Strategy
description: Pratiques de test de niveau senior, insistance sur la séparation des fichiers, le typage strict et la qualité du code de test.
---

# Stratégie de Test - Niveau Senior

Cette compétence définit les règles incontournables pour l'écriture de tests automatisés maintenables, propres et robustes.

## 1. Séparation Stricte des Fichiers
- **Correspondance 1-to-1** : Chaque classe de production doit avoir sa propre classe de test dédiée (ex: `ZombieService` -> `ZombieServiceTest`).
- **Tests Unitaires vs Intégration** : Ne **jamais** mélanger les tests unitaires et les tests d'intégration dans le même fichier. Séparez-les par conventions de nommage (ex: `*Test.java` vs `*IT.java`) ou par packages.
- **Utilitaires séparés** : Tout code utilitaire (helpers, factories, configurations) doit être extrait dans des classes et fichiers dédiés, jamais noyé dans le fichier de test.

## 2. Typage Strict et Propreté
- **Pas de typage faible** : Interdiction d'utiliser des casts sauvages, des types génériques non spécifiés ou des types `Object` bruts. Le compilateur doit garantir la sécurité des types dans les tests comme dans la production.
- **Clean Code dans les tests** : Le code de test doit être traité avec le même niveau d'exigence que le code de production (DRY, refactoring continu, pas de code mort).

## 3. Structure Arrange-Act-Assert (AAA)
Chaque bloc de test doit être visuellement scindé en 3 parties claires (via des sauts de ligne ou des commentaires) :
1. **Arrange** : Mise en place des données, initialisation des mocks et factories.
2. **Act** : L'appel unique de la méthode ou du comportement testé.
3. **Assert** : Vérification des résultats et des appels aux mocks. Un seul concept vérifié par test.

## 4. Nommage Expressif
Les méthodes de test doivent lire comme une phrase décrivant le comportement attendu.
*Exemple attendu* : `should_throw_exception_when_user_not_found_in_database()`
*À proscrire* : `testUserNotFound()`, `test1()`
