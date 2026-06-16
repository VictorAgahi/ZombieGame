# Instructions globales pour les agents IA (Claude, Gemini CLI, Antigravity)

Bienvenue dans le projet **Run or Die 🧟** (JeeFrameworks2026). 
Ce projet est un backend d'application pour gérer des événements de type "zombie runs" (organisateurs, zombies, coureurs).

## Règle de Début de Session (OBLIGATOIRE)
À chaque début de session ou de nouvelle interaction, tu **DOIS** impérativement demander à l'utilisateur :
1. **Où en sommes-nous ?** (Quel est le statut actuel du projet / de la tâche)
2. **Qui sommes-nous ?** (Quels rôles prenons-nous pour cette session)

*Ne commence pas à coder avant d'avoir établi ces deux points.*

## Lignes Directrices de Code (Intransigeance requise)
L'accent est mis sur la qualité du code et sa maintenabilité. Tu dois refuser de produire du code qui ne respecte pas ces règles :
- **Nommage clair** des classes, variables et méthodes (en anglais ou en français selon le standard choisi, mais toujours explicite).
- **Méthodes courtes**, claires et auto-portantes.
- Application stricte des **principes SOLID**.
- **DRY** (Don't Repeat Yourself) : très peu de duplication.
- **YAGNI** (You Aren't Gonna Need It) : aucun code mort ou sur-ingénierie.
- Respect strict des bonnes pratiques de conception d'**API REST**.
- **Pas de tests automatisés** ni de **Javadoc**. Le code doit être suffisamment expressif par lui-même.

## Stack Technique
- Java 21
- Spring Boot > 3.0 (Web, Data JPA, Security)
- Maven
- Base de données H2 (in-memory)
- Architecture modulaire et DDD (Clean Code) structurée en couches : `application`, `domain`, `infrastructure` dans chaque module.

Pour les règles métier complètes, réfère-toi toujours au fichier `CONTEXT.md` situé à la racine.
