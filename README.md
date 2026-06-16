# Run or Die 🧟

Backend d'une application pour gérer des éditions d'événements sportifs décalés : les zombie runs.

## Équipe
- Victor Agahi (victor.agahi@epita.fr)
- Clément Pasteau (clement.pasteau@epita.fr)
- Victor Giroud (victor.giroud@epita.fr)
- Damien Lugagne-Delpon (damien.lugagne-delpon@epita.fr)

## Lancement et Déploiement

L'application est construite avec Maven.

Pour compiler le projet et générer l'archive finale :
```bash
mvn clean install
```
Cette commande va :
1. Compiler tous les modules de l'application.
2. Générer le *fat-jar* auto-exécutable.
3. Copier automatiquement ce jar dans le dossier `/jar` situé à la racine du projet.

Pour lancer l'application (une fois compilée) :
```bash
java -jar jar/run-or-die-1.0.0-SNAPSHOT.jar
```

## Accès et Données de Test

Le jeu de données initial charge automatiquement deux utilisateurs en mémoire :
- **Organisateur** : `mastermind@epita.fr` / `brains`
- **Coureur** : `forrest@epita.fr` / `run`

Une fois l'application démarrée, l'interface **Swagger** pour explorer et tester l'API est accessible à l'adresse suivante :
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Architecture Modulaire et DDD

Le projet est divisé en 5 modules Maven représentant les différents domaines fonctionnels et l'assemblage de l'application :

```mermaid
graph TD
    A["Run Or Die (Parent POM)"] --> APP["App Module (Launcher)"]
    A --> B["User Module"]
    A --> C["Edition Module"]
    A --> D["Zombie Module"]
    A --> E["Coureur Module"]
    
    APP -.->|Assemble| B
    APP -.->|Assemble| C
    APP -.->|Assemble| D
    APP -.->|Assemble| E
    
    C -.->|Utilise| B
    D -.->|S'inscrit à| C
    E -.->|S'inscrit à| C
    D -.->|Hérite/Utilise| B
    E -.->|Hérite/Utilise| B
```

Chaque module respecte une architecture **Clean Code / DDD** stricte avec les couches suivantes :
- **`application`** : `controllers`, `dtos` (`requests` et `responses`), `mappers`. Couche d'exposition et d'interaction avec l'extérieur.
- **`domain`** : `entities` (objets métier) et `services` (logique métier). Cœur isolé des frameworks externes.
- **`infrastructure`** : `repositories` (accès aux données), `models` (modèles de persistance) et configuration.

```mermaid
graph TD
    subgraph Application
        C[Controllers]
        D[DTOs]
        M[Mappers]
    end

    subgraph Domain
        S[Services]
        E[Entities]
    end

    subgraph Infrastructure
        R[Repositories]
        Mo[Models]
        Cfg[Config]
    end

    C -->|Appelle| S
    M -.->|Transforme| D
    M -.->|Transforme| E
    R -->|Persiste| E
    S -->|Orchestre| E
```

## Modèle de Données (Prévisionnel)

```mermaid
erDiagram
    USER {
        Long id
        String email
        String password
        String role
    }
    EDITION {
        Long id
        String name
        Date date
        Time startTime
        Time endTime
        String location
        Int maxRunners
        Int maxZombies
    }
    ZOMBIE {
        Long id
        Time startTime
        Time endTime
    }
    COUREUR {
        Long id
        String licenseStatus
    }
    
    USER ||--o{ ZOMBIE : "peut endosser le rôle"
    USER ||--o{ COUREUR : "peut endosser le rôle"
    EDITION ||--o{ ZOMBIE : "accueille"
    EDITION ||--o{ COUREUR : "accueille"
```

## Stack Technique
- Java 21
- Spring Boot > 3.0
- H2 Database (in-memory)
- OpenAPI (Swagger UI)
- Maven (Multi-modules)
