# Utilisation de RTK (Rust Token Killer)

Dans ce projet, il est **obligatoire** d'utiliser RTK, un proxy CLI optimisé pour économiser les tokens (60-90% d'économies).

## Directives pour l'Agent
- Utiliser les commandes méta RTK pour vérifier et analyser l'usage :
  - `rtk gain` : Affiche les statistiques d'économie de tokens.
  - `rtk gain --history` : Affiche l'historique d'utilisation et les économies.
  - `rtk discover` : Analyse l'historique pour trouver des opportunités manquées.
  - `rtk proxy <cmd>` : Exécute une commande brute sans filtrage (pour le débogage).
- **Vérification de l'installation** : Si besoin, tu peux vérifier que rtk est bien installé via `rtk --version` ou `which rtk`. Attention aux collisions de noms (ne pas confondre avec Rust Type Kit).
- **Usage via Hook** : L'utilisation générale des commandes est automatiquement réécrite en arrière-plan (ex: `git status` -> `rtk git status`), mais tu dois garder en tête que cet outil est actif.
