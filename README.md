# Materiaalverhuur Platform - Kunstacademie

## 📋 Project Overzicht

Dit project is een webapplicatie ontwikkeld voor een kunstopleiding waar studenten materiaal kunnen reserveren en huren voor hun projecten en eindwerken. Het platform biedt een gebruiksvriendelijke catalogus met diverse apparatuur zoals lampen, podiumelementen, lichtpanelen en kabels.

### Projectcontext

De kunstacademie wilde een digitaal platform om het verhuurproces van materiaal te stroomlijnen. Studenten kunnen via deze applicatie zelfstandig materiaal reserveren voor specifieke periodes en ontvangen direct een bevestiging van hun reservatie.

### Proof of Concept

Dit is een **proof of concept** met een beperkte catalogus van ongeveer 10 diverse toestellen en accessoires, verdeeld over verschillende categorieën. De focus ligt op het demonstreren van de kernfunctionaliteiten van het verhuursysteem.

---

## Functionele Vereisten (Volledig Geïmplementeerd)

- **Volledige catalogus**: Overzicht van alle beschikbare materialen
- **Categorie filtering**: Filteren op Belichting, Kabels, Controlepanelen en Podiumelementen
- **Zoekfunctie**: Zoeken in productnamen en beschrijvingen
- **Winkelmandje**: Toevoegen, verwijderen en beheren van reservaties
- **Checkout systeem**: Bevestigingspagina met uniek reservatienummer
- **Gebruikersregistratie**: Volledige registratie voor nieuwe studenten
- **Veilig login systeem**: Spring Security met BCrypt password hashing en automatische salting

---

## 🛠️ Technologie Stack

### Backend
- **Java 17/21**
- **Spring Boot 4.0.0**
- **Spring Security 7.0.0**
- **Spring Data JPA**
- **Hibernate**
- **BCrypt**

### Frontend
- **Thymeleaf**
- **Bootstrap 5**
- **HTML5 & CSS3**

### Database
- **MySQL 8**
- **WSL Linux omgeving**

### Development Tools
- **Maven**
- **Lombok**
- **IntelliJ IDEA**

---

## 📁 Project Structuur

src/main/java/be/kunstacademie/materiaalverhuur/
├── config/
│ ├── SecurityConfig.java
│ └── DataInitializer.java
├── controller/
│ ├── ProductController.java
│ ├── CartController.java
│ ├── AuthController.java
│ ├── CheckoutController.java
│ └── AdminController.java
├── model/
│ ├── Product.java
│ ├── Category.java
│ ├── User.java
│ ├── CartItem.java
│ ├── Order.java
│ └── OrderItem.java
├── repository/
│ ├── ProductRepository.java
│ ├── CategoryRepository.java
│ ├── UserRepository.java
│ ├── CartItemRepository.java
│ └── OrderRepository.java
├── service/
│ ├── ProductService.java
│ ├── CartService.java
│ ├── UserService.java
│ └── OrderService.java
└── EnterpriseAppEindopdrachtApplication.java

shell
Copier le code

### Frontend Templates
src/main/resources/templates/
├── catalog.html
├── product-detail.html
├── cart.html
├── checkout.html
├── confirmation.html
├── login.html
├── register.html
└── orders.html

yaml
Copier le code

---

## 🔧 Setup Instructies

### 1. Vereisten
- Java 17+
- Maven 3.8+
- IntelliJ IDEA
- WSL + Ubuntu
- MySQL Server

### 2. MySQL Setup via WSL

#### Installatie

```bash
wsl --install
sudo apt update && sudo apt upgrade
sudo apt install mysql-server
sudo mysql_secure_installation
Root configuratie
bash
Copier le code
sudo mysql
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Testpassword1234!';
exit
Database aanmaken
bash
Copier le code
sudo mysql -u root -p
CREATE DATABASE materiaalverhuur;
USE materiaalverhuur;
Database Schema
users, user_roles, categories, products,
cart_items, orders, order_items

Relaties:

scss
Copier le code
User (1) ─── (N) CartItem (N) ─── (1) Product
User (1) ─── (N) Order (1) ─── (N) OrderItem ─── (1) Product
Product (N) ─── (1) Category
Security Implementatie
BCrypt Password Hashing
Automatische salt

Adaptive hashing

Rainbow table resistant

Voorbeeld:

perl
Copier le code
$2a$10$N9qo8uLOickgx2ZMRZoMye/IiefvwHBXQRfD8.qXN8k2Jz2w6N.yC
Spring Security
Form login

Custom login pagina

Role-based access

CSRF enabled

BCrypt encoder

 Frontend (Thymeleaf + Bootstrap)
Belangrijkste features:

Loops & conditionals

Form binding

URL helpers

Responsive UI

Testing & Development
Testdata via DataInitializer

Admin test endpoints:

/admin/count

/admin/init-data

/admin/delete-all

Bronnen & Tutorials
Gebruikte Bronnen & Referenties
🔹 Officiële Documentatie

Spring Boot Documentation
https://docs.spring.io/spring-boot/docs/current/reference/html/

Gebruikt voor algemene Spring Boot setup, application properties configuratie.

Spring Security Reference
https://docs.spring.io/spring-security/reference/index.html

Gebruikt voor security configuratie, BCrypt implementatie.

Spring Data JPA
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

Gebruikt voor repository patterns, custom queries, entity mappings.

Thymeleaf Documentation
https://www.thymeleaf.org/documentation.html

Gebruikt voor template syntax, form handling, conditionals.

Hibernate ORM Documentation
https://hibernate.org/orm/documentation/

Gebruikt voor entity-relaties, cascade types, fetch strategies.

🔹 Security & Best Practices

OWASP Password Storage Cheat Sheet
https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html

Gebruikt voor BCrypt best practices, salt usage, veilig wachtwoordbeheer.

BCrypt – Wikipedia
https://en.wikipedia.org/wiki/Bcrypt

Gebruikt voor conceptueel begrip van het algoritme.

Spring Security BCrypt Encoder API
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html

Gebruikt voor configuratie van BCryptPasswordEncoder.

🔹 Tutorials & Guides

Spring Boot + MySQL (Spring.io Guide)
https://spring.io/guides/gs/accessing-data-mysql/

Gebruikt voor database setup en JPA configuratie.

Baeldung – Spring Security met Thymeleaf
https://www.baeldung.com/spring-security-thymeleaf

Gebruikt voor login-form integratie.

Baeldung – Spring Boot Security Auto Configuration
https://www.baeldung.com/spring-boot-security-autoconfiguration

Gebruikt voor het begrijpen van security defaults.

Bootstrap 5 Documentation
https://getbootstrap.com/docs/5.3/

Gebruikt voor front-end layout, grids, formulieren, componenten.

🔹 Development Tools

Project Lombok
https://projectlombok.org/

Gebruikt voor @Data, @Getter, @Setter, @RequiredArgsConstructor.

MySQL via WSL (Microsoft Docs)
https://learn.microsoft.com/en-us/windows/wsl/tutorials/wsl-database

Gebruikt voor MySQL installatie & configuratie binnen WSL.

Maven Official Documentation
https://maven.apache.org/guides/

Gebruikt voor dependency-management en build lifecycle.

🔹 Code Voorbeelden & Inspiratie

Spring PetClinic (Sample Project)
https://github.com/spring-projects/spring-petclinic

Gebruikt voor projectstructuur en best-practice voorbeelden.

Spring Security Samples
https://github.com/spring-projects/spring-security-samples

Gebruikt voor configuratievoorbeelden en authentication flows.

🔹 Externe Video- & ChatGPT-bronnen

ChatGPT — Project Assistance
https://chatgpt.com/share/693c38a9-d170-8007-88f8-b2dd29979a3c

YouTube Tutorial 1 — Spring Boot Login/Register
https://www.youtube.com/watch?v=ig8k_5nEov4

YouTube Tutorial 2 — Spring Boot Security + BCrypt
https://www.youtube.com/watch?v=gJrjgg1KVL4





