# Protocole de test de l'application
| **Test à effectuer (Accueil)** | **Résultat attendu** | **Validation** | **Remarques**|
| ------------------------------ | -------------------- | -------------- | ------------ |
| Démarrage de l’application | L’application démarre | <ul><li>- [ ] Validé</li></ul> | |
| Installation de l’application | L’application possède une icône personnalisée | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur la roue dentée de paramètres | Les paramètres s’ouvres | <ul><li>- [ ] Validé</li></ul> | |
| Cliqué sur About | Une boite de dialogue avec la présentation de l’application, la version ainsi que l’auteur s’affiche. | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur OK dans la boîte de dialogue | La boîte de dialogue se ferme | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur le champ du joueur 2 alors que le champ du joueur 1 n’est pas rempli | La zone de texte est désactivée | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur start alors que les deux noms de joueur ne sont pas remplis | Le bouton est désactivé et apparaît grisé | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur start alors qu’un des noms de joueur n’est pas rempli | Le bouton est désactivé et apparaît grisé | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur start avec les deux noms de joueurs correctes | La fenêtre de jeu se lance | <ul><li>- [ ] Validé</li></ul> | |
| Au lancement de la fenêtre de jeux | Un minuteur de 6 secondes démarre | <ul><li>- [ ] Validé</li></ul> | |

| **Test à effectuer (Jeu)** | **Résultat attendu** | **Validation** | **Remarques**|
| -------------------------- | -------------------- | -------------- | ------------ |
| Au lancement de la fenêtre de jeux | Un minuteur de 6 secondes démarre. Les scores sont à 0 et les noms des joueurs sont affichés. Les boutons de validation sont grisés et désactivés | <ul><li>- [ ] Validé</li></ul> | |
| Au défilement des questions | La vitesse de changement des questions correspond à la vitesse spécifiée dans les paramètres | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur un bouton de validation alors que l’affirmation n’est pas vraie | Le joueur perd 1 point et les deux boutons sont désactivés et grisés | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur un bouton de validation alors que l’affirmation est vraie | Le joueur gagne 1 point et les deux boutons sont désactivés et grisés | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur un bouton de validation alors que la question a déjà été répondue | Rien ne se passe | <ul><li>- [ ] Validé</li></ul> | |
| Fin de la partie | Le nom du jouer gagnant ou égalité est affiché. Le bouton menu et rejouer s’affichent. Les boutons de validations sont désactivés et grisés | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur le bouton menu | La fenêtre de jeux se ferme et l’on se retrouve sur l’activité main | <ul><li>- [ ] Validé</li></ul> | |
| Cliquer sur le bouton rejouer | Une nouvelle partie se lance comme si on relançait l’activité | <ul><li>- [ ] Validé</li></ul> | |

| **Test à effectuer (Paramètres)** | **Résultat attendu** | **Validation** | **Remarques**|
| --------------------------------- | -------------------- | -------------- | ------------ |
| Modifier le slider de vitesse des questions | Lors de la réouverture de l’application les paramètres sont sauvegardés.  | <ul><li>- [ ] Validé</li></ul> | |
| Clique sur le switch d’ajout de question | Affiche la possibilité d’ajouter une question | <ul><li>- [ ] Validé</li></ul> | |
| Clique sur le bouton d’ajout de question sans que le champ de saisie ne soit rempli | Le bouton est désactivé et apparaît grisé | <ul><li>- [ ] Validé</li></ul> | |
| Clique sur le bouton d’ajout de question quand le champ de saisie est rempli | La question s’ajoute dans la base de données en avec la bonne réponse  | <ul><li>- [ ] Validé</li></ul> | |
| Clique sur le bouton fermé  | L’activité se ferme. Si une question était présente elle n’est pas enregistrée dans la DB | <ul><li>- [ ] Validé</li></ul> | |
| Clique sur le bouton de fermeture à côté du titre de l’activité | L’activité se ferme. Si une question était présente elle n’est pas enregistrée dans la DB | <ul><li>- [ ] Validé</li></ul> | |
| Ajouter la question avec une réponse vrai | Lorsqu’on joue au jeux la réponse est vrai | <ul><li>- [ ] Validé</li></ul> | |
| Ajouter la question avec une réponse fausse | Lorsqu’on joue au jeux la réponse est fausse | <ul><li>- [ ] Validé</li></ul> | |
