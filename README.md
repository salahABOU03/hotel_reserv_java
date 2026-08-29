# 🏨 Hotel Reservation Management System — Java Swing GUI & Generic DAO

> Application de bureau complète pour la gestion hôtelière développée en **Java (Swing / AWT)**, connectée à **MySQL** via **JDBC**, intégrant une architecture **Generic DAO**, un sélecteur de dates **JCalendar** et un module de notification par email (**JavaMail**).

![Java](https://img.shields.io/badge/Java-Swing_/_AWT-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Design Pattern](https://img.shields.io/badge/Pattern-Generic_DAO_<T>-59666C?style=for-the-badge)
![MySQL](https://img.shields.io/badge/Database-MySQL_8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JCalendar](https://img.shields.io/badge/UI_Widget-JCalendar_1.4-FF9900?style=for-the-badge)
![JavaMail](https://img.shields.io/badge/Notification-JavaMail_API-EA4335?style=for-the-badge&logo=gmail&logoColor=white)

---

## 📌 Présentation du Projet

Ce système logiciel offre une interface graphique moderne et intuitive permettant au personnel de réception et aux gestionnaires d'un établissement hôtelier d'administrer l'ensemble des flux opérationnels :

- 🔐 **Authentification Administrateur** : Écran de connexion sécurisé (`AdminLoginForm`) pour l'accès aux fonctionnalités de gestion.
- 🛏️ **Inventaire des Chambres & Catégories** : Gestion dynamique des types de chambres (Simple, Double, Suite, VIP) et assignation des lignes téléphoniques et équipements.
- 👥 **Gestion du Fichier Clients** : Enregistrement, modification et suivi des coordonnées des clients (Nom, Prénom, Téléphone, Email).
- 📅 **Moteur de Réservation & Calendrier** : Réservation de chambres sur une période donnée avec sélecteur de date interactif **JCalendar** et rendu visuel personnalisé (`ChambreRenderer`).
- ✉️ **Confirmations par Email** : Envoi automatique de notifications de confirmation aux clients grâce à l'API **JavaMail**.

---

## 🏗️ Architecture Technique & Structure du Code

L'application repose sur le pattern architectural **MVC** et implémente une couche d'accès aux données générique **`IDAO<T>`** :

```
hotel_reserv_java/
├── lib/                                      # Librairies et dépendances JAR externes
│   ├── mysql-connector-java-8.0.30.jar       # Driver JDBC MySQL
│   ├── jcalendar-1.4.jar                     # Composant graphique de calendrier
│   ├── javax.mail.jar                        # API de messagerie SMTP
│   └── javax.activation.jar                  # Framework JavaBeans Activation
│
├── src/
│   ├── App.java                              # Point d'entrée de l'application
│   ├── presentation/
│   │   └── Main.java                         # Classe de lancement
│   ├── connexion/
│   │   └── Connexion.java                    # Singleton de gestion de la connexion JDBC MySQL
│   ├── dao/
│   │   └── IDAO.java                         # Interface générique CRUD (create, update, delete, findById, findAll)
│   ├── entities/                             # Entités métiers (Modèles relationnels)
│   │   ├── Client.java                       # Modèle Client (id, nom, prenom, telephone, email)
│   │   ├── Categorie.java                    # Modèle Catégorie (id, code, libelle)
│   │   ├── Chambre.java                      # Modèle Chambre (id, telephone, Categorie)
│   │   └── Reservation.java                  # Modèle Réservation (id, datedebut, datefin, Chambre, Client)
│   ├── service/                              # Implémentations concrètes des services DAO
│   │   ├── ClientService.java                # Opérations CRUD Client (PreparedStatement SQL)
│   │   ├── CategorieService.java             # Opérations CRUD Catégorie
│   │   ├── ChambreService.java               # Opérations CRUD Chambre & jointures
│   │   └── ReservationService.java           # Opérations CRUD Réservations
│   └── GUI/                                  # Vues & Interfaces Graphiques Swing / AWT
│       ├── AdminLoginForm.java               # Fenêtre de connexion sécurisée
│       ├── MainGui.java                      # Tableau de bord principal avec barre latérale et JDesktopPane
│       ├── ClientForm.java & .form           # Gestion graphique des clients (Tableaux & Formulaires)
│       ├── ChambreForm.java & .form          # Gestion graphique des chambres
│       ├── CategorieForm.java & .form        # Gestion graphique des catégories
│       ├── ReservationForm.java & .form      # Formulaire de réservation
│       └── ReservationUI.java                # Interface avancée avec calendrier et ChambreRenderer
```

---

## 🗄️ Modèle de Données (Base MySQL `Gestionutilisateur`)

```
┌──────────────────┐          ┌──────────────────┐
│    Categorie     │          │      Client      │
├──────────────────┤          ├──────────────────┤
│ id (PK)          │          │ id (PK)          │
│ code             │          │ nom              │
│ libelle          │          │ prenom           │
└────────┬─────────┘          │ telephone        │
         │ 1                  │ email            │
         │                    └────────┬─────────┘
         │ N                           │ 1
┌────────┴─────────┐                   │
│     Chambre      │                   │
├──────────────────┤                   │
│ id (PK)          │                   │
│ telephone        │                   │
│ categorie_id (FK)│                   │
└────────┬─────────┘                   │
         │ 1                           │
         │                             │
         │ N                           │ N
┌────────┴─────────────────────────────┴─────────┐
│                  Reservation                   │
├────────────────────────────────────────────────┤
│ id (PK)                                        │
│ datedebut                                      │
│ datefin                                        │
│ chambre_id (FK)                                │
│ client_id (FK)                                 │
└────────────────────────────────────────────────┘
```

---

## ✨ Points Forts du Code

- 🔄 **Interface Générique `IDAO<T>`** : Factorisation des méthodes `create(T o)`, `update(T o)`, `delete(T o)`, `findById(int id)` et `findAll()`.
- 🔒 **Sécurité SQL** : Utilisation systématique de requêtes préparées (`PreparedStatement`) pour prévenir les injections SQL.
- 🎨 **Interface Moderne Swing** : Panneau latéral sombre (`#1E1E1E`), conteneur `JDesktopPane` MDI et sélection ergonomique des dates via `JCalendar`.
- 📧 **Automatisation** : Envoi de courriers électroniques avec l'API JavaMail lors de la validation d'une réservation.

---

## ⚙️ Installation & Lancement

### Prérequis
- **Java JDK 8 ou supérieur**
- **MySQL Server 5.7 / 8.0+**

### 1. Configuration de la base de données
Créer la base MySQL :
```sql
CREATE DATABASE Gestionutilisateur;
```

Ajuster `src/connexion/Connexion.java` si besoin :
```java
private static final String url = "jdbc:mysql://localhost:3306/Gestionutilisateur";
private static final String user = "root";
private static final String password = "votre_mot_de_passe";
```

### 2. Compilation et Exécution

#### Via ligne de commande :
```bash
# Cloner le dépôt
git clone https://github.com/salahABOU03/hotel_reserv_java.git
cd hotel_reserv_java

# Compiler avec le classpath incluant les JARs du dossier lib
javac -cp "lib/*;src" -d bin src/**/*.java

# Lancer l'application
java -cp "lib/*;bin" GUI.MainGui
```

#### Via Eclipse / VS Code :
1. Importer le projet comme **Java Project**.
2. Vérifier que les JARs de `lib/` sont ajoutés au **Java Build Path** (Referenced Libraries).
3. Exécuter `GUI.MainGui.java` ou `GUI.AdminLoginForm.java`.

---

## 👤 Auteur
**Salah Eddine Abouelkemhe** — Développeur Full-Stack & Spécialiste Java
