# Maîtrise du Domaine du Projet (Run or Die)

## Contexte
Le projet est le backend d'une plateforme d'organisation de "zombie runs". L'agent IA doit se référer au fichier `CONTEXT.md` pour toute question liée aux règles de gestion.

## Compétences Attendues
- **Analyse du domaine** : L'agent sait distinguer les entités principales : `Édition`, `Utilisateur` (Rôles: `Organisateur`, `Zombie`, `Coureur`).
- **Validation** : L'agent vérifie toujours les contraintes métier (ex: chevauchement des dates, capacité des événements, conflits d'inscriptions zombie/coureur) avant d'implémenter un service.
- **Bootstrapping** : L'agent sait que l'application doit intégrer un jeu de données de test en mémoire (H2) au lancement, comme spécifié dans les exigences (comptes prédéfinis `mastermind@epita.fr` et `forrest@epita.fr`).

## Consignes Techniques
- Maintenir une architecture Clean Code / DDD stricte dans chaque module, découpée en 3 dossiers principaux : `application` (controllers, dtos, mappers), `domain` (entities, services), et `infrastructure` (repositories, models).
- Bouchonner (mock via `System.out.println`) les services externes (comme les emails ou la vérification des licences).
