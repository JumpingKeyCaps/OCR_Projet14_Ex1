## Projet CRM Android

Ce projet est une application Android de gestion de la relation client (CRM) pour l'entreprise Relayance. Elle permet à l'entreprise de suivre efficacement le flux de travail des commerciaux concernant les propositions commerciales et de favoriser les retours de la direction.

### Fonctionnalités
- Affichage des clients : Liste tous les clients dans une vue principale.
- Détails des clients : Affiche les détails spécifiques d'un client sélectionné, tels que le nom et l'e-mail.
- Ajout et modification des clients : Permet d'ajouter de nouveaux clients.

### Technologie

L'application est développée en Kotlin et utilise les bibliothèques suivantes :

- Android SDK
- Material Components pour Android
- JUnit et Espresso pour les tests unitaires et d'intégration

### Prérequis

- Android Studio 4.0 ou version ultérieure
- Android SDK (API 24 ou ultérieure)
- JDK 8 ou version ultérieure

### Installation

Cloner le dépôt

git clone https://github.com/OpenClassrooms-Student-Center/relayance-kotlin.git

### Ouvrir dans Android Studio

Lancez Android Studio et choisissez "Open an existing Android Studio project". Naviguez jusqu'au dossier cloné et ouvrez-le.

Compiler et exécuter

Assurez-vous qu'un dispositif Android ou un émulateur est configuré. Compilez et exécutez l'application via Android Studio avec le bouton 'Run'.

### Utilisation

Vue principale : À l'ouverture de l'application, vous êtes accueilli par la liste des clients. Sélectionnez un client pour voir ses détails.
Détails du client : Affiche le nom et l'e-mail du client. Utilisez le bouton de retour pour revenir à la liste principale.
Ajouter/Modifier un client : Accédez à cette fonctionnalité via le FloatingActionButton (FAB) sur la vue principale. Remplissez les informations et sauvegardez.
