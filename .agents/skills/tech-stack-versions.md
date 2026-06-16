# Stack Technique et Versions Officielles

Le projet **Run or Die** s'appuie sur une stack technologique précise. L'agent IA doit formuler ses réponses, son code et ses configurations Maven en respectant strictement ces versions :

- **Java** : 21 (Utiliser les fonctionnalités modernes du langage, ex: pattern matching, records si pertinent).
- **Spring Boot** : > 3.0 (Attention aux packages `jakarta.*` qui remplacent `javax.*`, et aux nouvelles configurations de Spring Security).
- **Dépendances principales** :
  - Spring Web
  - Spring Data JPA
  - Spring Security
- **Base de données** : H2 Database (base de données en mémoire pour simplifier le setup).
- **Build tool** : Maven (Le projet doit être modélisé en 4 couches distinctes via des modules Maven si l'architecture le demande, ou un `pom.xml` global bien structuré).

L'agent ne doit **jamais** proposer des librairies obsolètes ou des versions de Java antérieures.
