---
name: Test Doubles (Mocks & Spies)
description: Comment utiliser correctement les mocks et les spies en Java (Mockito) pour isoler les tests unitaires de manière propre.
---

# Mocks et Spies - Pratiques Senior

L'utilisation de doubles de test (Test Doubles) doit être maîtrisée pour éviter des tests fragiles et couplés à l'implémentation.

## 1. Règle d'or : Ne pas "Over-mocker"
- Ne mocker **que** les dépendances externes (bases de données, appels réseau, services d'infrastructure).
- Les objets du domaine (Entities, Value Objects) ne doivent **jamais** être mockés. Utilisez de vraies instances (idéalement via des Factories).
- Si un test requiert trop de mocks (plus de 3 ou 4), c'est un "Code Smell" indiquant que la classe testée a probablement trop de responsabilités.

## 2. Utilisation des Mocks
- En Java (avec Mockito), utilisez les annotations `@Mock` et `@InjectMocks` pour plus de clarté.
- Séparez toujours la configuration des mocks du corps du test.
- Vérifiez strictement les interactions critiques avec `verify()`, mais ne vérifiez pas tout systématiquement au risque de rendre le test rigide.

## 3. Utilisation des Spies (Mocks Partiels)
- À utiliser avec **extrême parcimonie**. Un Spy permet d'espionner un objet réel, c'est utile surtout pour tester du code "legacy" ou interagir avec des wrappers.
- Préférer toujours un refactoring de la conception pour injecter une dépendance plutôt que d'utiliser un Spy pour surcharger le comportement interne d'une classe.
- Si un Spy est nécessaire, documentez explicitement la raison de son utilisation dans un commentaire.

## 4. Isolation
- Assurez-vous que l'état des mocks est réinitialisé entre chaque test (généralement géré automatiquement par les extensions JUnit 5 comme `MockitoExtension`).
