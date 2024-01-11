# Tasks Stack 'N Track

## Description

Tasks Stack 'N Track est une application de gestion de tâches, permettant de créer des tâches et de leur assigner des propriétés personnalisées.

**/!\ L'application est en cours de développement.**

L'intérêt principal de cette application sera de pouvoir fusionner différentes tâches en une seule, en fusionnant leurs propriétés respectives.

Par exemple, à partir de deux tâches "Recette de gâteau" et "Recette de cookies", il sera possible de les fusionner en une seule tâche "Liste de courses", qui aura les ingrédients des deux tâches parentes, et les ingrédients en commun seront fusionnés, voyant leurs quantités additionnées.

## Installation

### Prérequis

- Java 17+
- Maven
- npm

## Utilisation

### Lancement de l'application

Pour lancer l'application, il faut d'abord lancer le serveur Spring Boot, puis le serveur React.

#### Lancement du serveur Spring Boot

Pour lancer le serveur Spring Boot, il faut exécuter la commande suivante à la racine du projet :

```bash
mvn spring-boot:run
```

#### Lancement du serveur React

Pour lancer le serveur React, il faut exécuter la commande suivante depuis l'intérieur du dossier `frontend` dans un terminal séparé:

```bash
npm install
npm run build
npm start
```

### Utilisation de l'application

Une fois les deux serveurs lancés, il est possible d'accéder à l'application à l'adresse suivante : http://localhost:3000/

L'application est composée d'une page principale, qui affiche la liste des tâches existantes, et permet d'en créer de nouvelles.

Chaque tâche peut être éditée ou supprimée.

## Fonctionnalités prévues

- [x] Création de tâches
- [ ] Édition de tâches
  - [x] Édition du titre
  - [x] Édition de la description
  - [ ] Édition des propriétés
    - [ ] Ajout d'une propriété
    - [x] Édition de la valeur associée à une propriété existante
    - [x] Suppression d'une propriété
- [x] Suppression de tâches
- [ ] Fusion de tâches
- [ ] Filtrage de tâches / recherche

## Description technique

### Technologies utilisées

- **Back-end et API**
  - Java 17
  - Spring Boot
  - Spring Data JPA (Hibernate)
  - H2 Database
  - Maven
  - JUnit
  - Lombok
  - MapStruct (mapping entre entités et DTOs)

- **Front-end**
  - JavaScript
  - React
  - Bootstrap
  - npm
  - Toastify

### Description de l'API

#### - `GET /api/tasks`
- **Rôle :** Récupère toutes les tâches.
- **Paramètres :** Aucun.
- **Réponse :** Un tableau de tâches.
- **Code de retour :** 200.

#### - `GET /api/tasks/{id}`
- **Rôle :** Récupère une tâche par son identifiant.
- **Paramètres :** `id` : Identifiant de la tâche à récupérer.
- **Réponse :** La tâche correspondant à l'identifiant.
- **Code de retour :** 200 si la tâche existe.

#### - `POST /api/tasks`
- **Rôle :** Crée une nouvelle tâche.
- **Paramètres :** Un objet JSON représentant la tâche à créer.
- **Réponse :** L'ID de la tâche créée.
- **Code de retour :** 201 si la tâche a été créée.

#### - `PUT /api/tasks/{id}`
- **Rôle :** Met à jour une tâche existante.
- **Paramètres :** `id` : Identifiant de la tâche à mettre à jour, et un objet JSON représentant la tâche à mettre à jour.
- **Réponse :** La tâche mise à jour.
- **Code de retour :** 200 si la tâche existe.

#### - `DELETE /api/tasks/{id}`
- **Rôle :** Supprime une tâche existante.
- **Paramètres :** `id` : Identifiant de la tâche à supprimer.
- **Réponse :** Aucune.
- **Code de retour :** 204 si la tâche a été supprimée.

### Description du modèle de données

#### - Entité `Task`
- **Rôle :** Représente une tâche individuelle avec des attributs comme le titre (obligatoire) et la description (optionnelle) au format chaîne de caractères.
- Une `Task` peut avoir plusieurs `TaskProperty` pour associer différentes propriétés à la tâche.

#### - Entité `TaskProperty`
- **Rôle :** Fait le lien entre une tâche et ses valeurs de propriétés spécifiques.
- `TaskProperty` se réfère à une `PropertyValue` pour obtenir la valeur effective de la propriété associée à une tâche.
- `TaskProperty` stock une référence à sa `Task` parente.

#### - Entité `PropertyValue`
- **Rôle :** Stocke la valeur spécifique d'une propriété pour une association donnée de tâche-propriété.
- Chaque `PropertyValue` est associée à une ou plusieurs `Property`, permettant de lier des valeurs spécifiques à des propriétés.
- Elle a une valeur au format au format chaîne de caractères qui permet de stocker une représentation de sa valeur.

#### - Entité `Property`
- **Rôle :** Définit une propriété générique qui peut être assignée à des tâches, comme "Durée" ou "Date d'échéance".
- Une `Property` peut avoir plusieurs `PropertyValue`, représentant différentes valeurs pour cette propriété.
- Une `Property`a un nom au format chaîne de caractères qui permet de nommer la propriété sur l'interface utilisateur.