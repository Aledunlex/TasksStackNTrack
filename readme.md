# TasksStackNTrack

## Description

TasksStackNTrack est une application de gestion de tâches, permettant de créer des tâches et de leur assigner des propriétés personnalisées.

L'application est en cours de développement.

L'intérêt principal de cette application sera de pouvoir fusionner différentes tâches en une seule, en fusionnant leurs propriétés respectives.

Par exemple, à partir deux tâches "Recette de gâteau" et "Recette de cookies", on pourrait les fusionner en une seule tâche "Liste de courses", qui aura les ingrédients des deux tâches parentes, et les ingrédients en commun seront fusionnés, voyant les quantités additionnées.

## Installation

### Prérequis

- Java 17+

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
  - TODO

### Description du modèle de données

#### - Entité `Task`
- **Rôle :** Représente une tâche individuelle avec des attributs comme le titre et la description.
- Une `Task` peut avoir plusieurs `TaskProperty` pour associer différentes propriétés à la tâche.

#### - Entité `TaskProperty`
- **Rôle :** Fait le lien entre une tâche et ses valeurs de propriétés spécifiques.
- `TaskProperty` se réfère à une `PropertyValue` pour obtenir la valeur effective de la propriété associée à une tâche.
- `TaskProperty` stock une référence à sa `Task` parente.

#### - Entité `PropertyValue`
- **Rôle :** Stocke la valeur spécifique d'une propriété pour une association donnée de tâche-propriété.
- Chaque `PropertyValue` est associée à une ou plusieurs `TaskProperty`, permettant de lier des valeurs spécifiques à des tâches.

#### - Entité `Property`
- **Rôle :** Définit une propriété générique qui peut être assignée à des tâches, comme "Durée" ou "Date d'échéance".
- Une `Property` peut avoir plusieurs `PropertyValue`, représentant différentes valeurs pour cette propriété.
