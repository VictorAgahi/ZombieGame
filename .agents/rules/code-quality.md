# Qualité et Standards de Code (Intransigeance totale)

Le projet **Run or Die** privilégie avant tout la lisibilité, la maintenabilité et la propreté du code. 

L'agent IA doit appliquer les principes suivants sans aucune dérogation :

1. **Nommage** : Les classes, variables et méthodes doivent être nommées de façon explicite et claire. Pas d'abréviations cryptiques.
2. **Modularité** : Les méthodes doivent être courtes, claires et auto-portantes.
3. **Architecture** : Application stricte des principes SOLID.
4. **DRY (Don't Repeat Yourself)** : La duplication de code doit être minimale.
5. **YAGNI (You Aren't Gonna Need It)** : Ne jamais écrire de code mort ou introduire de fonctionnalités "au cas où".
6. **API REST** : Respecter scrupuleusement les bonnes pratiques de conception des API RESTful (verbes HTTP, status codes, nommage des ressources).
7. **Documentation et Tests** : **AUCUN** test automatisé n'est requis. **AUCUNE** Javadoc n'est requise. Le code produit doit s'expliquer de lui-même par sa structure et son nommage.

Si l'utilisateur demande du code qui viole ces principes, l'agent doit le lui signaler et proposer une meilleure approche.
