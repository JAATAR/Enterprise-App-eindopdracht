# Materiaalverhuur Platform - Kunstacademie

##  Project Overzicht

Dit project is een webapplicatie ontwikkeld voor een kunstopleiding waar studenten materiaal kunnen reserveren en huren voor hun projecten en eindwerken. Het platform biedt een gebruiksvriendelijke catalogus met diverse apparatuur zoals lampen, podiumelementen, lichtpanelen en kabels.

### Projectcontext

De kunstacademie wilde een digitaal platform om het verhuurproces van materiaal te stroomlijnen. Studenten kunnen via deze applicatie zelfstandig materiaal reserveren voor specifieke periodes en ontvangen direct een bevestiging van hun reservatie.

### Proof of Concept

Dit is een **proof of concept** met een beperkte catalogus van ongeveer 10 diverse toestellen en accessoires, verdeeld over verschillende categorieën. De focus ligt op het demonstreren van de kernfunctionaliteiten van het verhuursysteem.

---

##  Functionele Vereisten (Volledig Geïmplementeerd)

-  **Volledige catalogus**: Overzicht van alle beschikbare materialen
-  **Categorie filtering**: Filteren op Belichting, Kabels, Controlepanelen en Podiumelementen
-  **Zoekfunctie**: Zoeken in productnamen en beschrijvingen
-  **Winkelmandje**: Toevoegen, verwijderen en beheren van reservaties
-  **Checkout systeem**: Bevestigingspagina met uniek reservatienummer
-  **Gebruikersregistratie**: Volledige registratie voor nieuwe studenten
-  **Veilig login systeem**: Spring Security met BCrypt password hashing en automatische salting

---

##  Technologie Stack

### Backend
- **Java 17/21**
- **Spring Boot 4.0.0**
- **Spring Security 7.0.0** - Authenticatie en autorisatie
- **Spring Data JPA** - Database abstractie
- **Hibernate 7.1.8** - ORM Framework
- **BCrypt** - Password hashing met automatische salt generatie

### Frontend
- **Thymeleaf** - Server-side template engine
- **Bootstrap 5.3.0** - CSS framework voor responsive design
- **HTML5 & CSS3**

### Database
- **MySQL 8.0** - Relationele database
- **WSL (Windows Subsystem for Linux)** - Linux omgeving voor MySQL server

### Build & Development Tools
- **Maven** - Dependency management en build tool
- **Lombok** - Boilerplate code reductie
- **IntelliJ IDEA** - IDE

---

##  Project Structuur
```
src/main/java/be/kunstacademie/materiaalverhuur/
├── config/
│   ├── SecurityConfig.java           # Spring Security configuratie
│   └── DataInitializer.java          # Test data initialisatie
├── controller/
│   ├── ProductController.java        # Catalogus en product endpoints
│   ├── CartController.java           # Winkelmandje functionaliteit
│   ├── AuthController.java           # Login en registratie
│   ├── CheckoutController.java       # Checkout proces
│   └── AdminController.java          # Admin/test endpoints
├── model/
│   ├── Product.java                  # Product entiteit
│   ├── Category.java                 # Categorie entiteit
│   ├── User.java                     # Gebruiker entiteit
│   ├── CartItem.java                 # Winkelmandje item
│   ├── Order.java                    # Bestelling entiteit
│   └── OrderItem.java                # Bestelling detail
├── repository/
│   ├── ProductRepository.java        # Product data access
│   ├── CategoryRepository.java       # Categorie data access
│   ├── UserRepository.java           # User data access
│   ├── CartItemRepository.java       # Cart data access
│   └── OrderRepository.java          # Order data access
├── service/
│   ├── ProductService.java           # Product business logica
│   ├── CartService.java              # Cart business logica
│   ├── UserService.java              # User management & authenticatie
│   └── OrderService.java             # Order processing
└── EnterpriseAppEindopdrachtApplication.java
```

### Frontend Templates
```
src/main/resources/templates/
├── catalog.html                      # Product catalogus
├── product-detail.html               # Product detail pagina
├── cart.html                         # Winkelmandje
├── checkout.html                     # Checkout pagina
├── confirmation.html                 # Bevestigingspagina
├── login.html                        # Login formulier
├── register.html                     # Registratie formulier
└── orders.html                       # Bestellingsoverzicht
```

---

## 🔧 Setup Instructies

### 1. Vereisten

- **Java JDK 17 of hoger** geïnstalleerd
- **Maven 3.8+** geïnstalleerd
- **IntelliJ IDEA** (aanbevolen)
- **WSL** voor Windows gebruikers
- **MySQL Server 8.0+**

### 2. MySQL Database Setup via WSL

#### WSL Installatie (Windows)
```bash
# In PowerShell (als Administrator)
wsl --install
```

#### Linux & MySQL Setup
```bash
# Update packages
sudo apt update
sudo apt upgrade

# Installeer MySQL
sudo apt install mysql-server

# Beveilig MySQL installatie
sudo mysql_secure_installation
```

**Opmerking**: Password validation mag op LOW voor development.

#### MySQL Root User Configuratie
```bash
# Start MySQL
sudo mysql

# Configureer root wachtwoord
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Testpassword1234!';
exit
```

#### MySQL Autostart (Optioneel)
```bash
# MySQL automatisch starten bij WSL boot
sudo update-rc.d mysql defaults
```

#### Database Aanmaken
```bash
# Start MySQL server
sudo service mysql start

# Login
sudo mysql -u root -p
# Wachtwoord: Testpassword1234!

# Maak database aan
CREATE DATABASE materiaalverhuur;
USE materiaalverhuur;
exit
```

**Alternatief**: Maak een aparte database user aan (aanbevolen voor productie):
```sql
CREATE DATABASE materiaalverhuur;
CREATE USER 'springuser'@'localhost' IDENTIFIED BY 'ThePassword';
GRANT ALL PRIVILEGES ON materiaalverhuur.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Project Setup in IntelliJ IDEA

1. **Clone of open het project**
   - File → Open → Selecteer project folder

2. **Maven Dependencies**
   - Maven zal automatisch dependencies downloaden
   - Wacht tot indexering compleet is

3. **Lombok Plugin**
   - Settings → Plugins → Zoek "Lombok"
   - Installeer en herstart IntelliJ
   - Settings → Build → Compiler → Annotation Processors
   - Vink aan: "Enable annotation processing"

### 4. Configuratie

Bewerk `src/main/resources/application.properties`:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/materiaalverhuur
spring.datasource.username=root
spring.datasource.password=Testpassword1234!
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=9000

# Thymeleaf Configuration
spring.thymeleaf.cache=false
```

**Belangrijk**: Pas gebruikersnaam en wachtwoord aan indien je andere credentials gebruikt.

### 5. Applicatie Starten

**Via IntelliJ:**
- Klik op groene Play knop naast `EnterpriseAppEindopdrachtApplication`
- Of: Rechtermuisknop op main class → Run

**Via Terminal:**
```bash
./mvnw spring-boot:run
```

### 6. Test Data Initialisatie

De applicatie maakt automatisch test data aan bij eerste opstart via `DataInitializer.java`.

**Handmatig data toevoegen** (indien nodig):
```
http://localhost:9000/login
```



### 7. Toegang tot Applicatie

- **URL**: `http://localhost:9000`
- **Test Account**:
  - Username: `student`
  - Password: `password123`
  - Email: `student@kunstacademie.be`

---

##  Database Schema

Het systeem gebruikt volgende database tabellen (automatisch gegenereerd door Hibernate):

### Entiteiten

**users**
- User accounts met BCrypt ge-hashte wachtwoorden
- Bevat: username, email, password (hashed), firstName, lastName, studentNumber

**user_roles**
- Junction table voor user rollen
- Mogelijke rollen: STUDENT, ADMIN

**categories**
- Product categorieën (Belichting, Kabels, Controlepanelen, Podiumelementen)

**products**
- Verhuurbaar materiaal met prijs per dag en voorraad

**cart_items**
- Tijdelijke reservaties in winkelmandje
- Bevat: product, quantity, startDate, endDate

**orders**
- Bevestigde reservaties met bevestigingsnummer

**order_items**
- Details van elke bestelling



---

##  Security Implementatie

### BCrypt Password Hashing

Het systeem gebruikt **BCrypt** voor veilige wachtwoord opslag:

#### Waarom BCrypt?

- **Automatische Salt Generatie**: Elke wachtwoord krijgt unieke salt
- **Adaptive Hashing**: 2^10 hashing rounds (configureerbaar)
- **Rainbow Table Resistant**: Door salting zijn rainbow table attacks onmogelijk
- **Future-Proof**: Cost factor kan verhoogd worden


#### Implementatie

**Bij registratie** (`UserService.java`):
```java
user.setPassword(passwordEncoder.encode(user.getPassword()));
```

**Bij login** (automatisch via Spring Security):
```java
passwordEncoder.matches(rawPassword, encodedPassword);
```

### Spring Security Configuratie

- **Form-based Authentication**: Custom login pagina
- **Session Management**: Max 1 sessie per gebruiker
- **Password Encoding**: BCrypt met strength 10
- **CSRF Protection**: Enabled (standaard)
- **Authorization**: Role-based access control

**Publieke endpoints**: `/`, `/register`, `/login`, `/products`  
**Beveiligde endpoints**: `/cart`, `/checkout`, `/orders`  
**Admin endpoints**: `/admin` (tijdelijk toegankelijk voor development)

---

##  Code Documentatie

### Belangrijke Classes & Methoden

#### ProductService.java
Service voor product-gerelateerde business logica.

**`searchProducts(String keyword)`**
- Zoekt producten op basis van keyword in naam en beschrijving
- Gebruikt JPQL query met LIKE operator (case-insensitive)
- Returns: Gefilterde lijst van producten

**`isProductAvailable(Long productId, int requestedQuantity)`**
- Controleert voorraad beschikbaarheid
- Business rule: Product moet available=true EN stock >= requested zijn
- Returns: Boolean indicator

#### CartService.java
Service voor winkelmandje beheer en prijsberekening.

**`addToCart(User user, Long productId, int quantity, LocalDate startDate, LocalDate endDate)`**
- Voegt product toe met validaties
- Validaties: voorraad check, datum validatie, quantity positief
- Berekent automatisch aantal verhuurdagen
- Throws: RuntimeException bij validatie fouten

**`calculateCartTotal(User user)`**
- Berekent totale prijs van winkelmandje
- Formule: Σ (pricePerDay × quantity × rentalDays) voor elk item
- Returns: BigDecimal voor nauwkeurige geldbedragen

#### UserService.java
Service voor user management en authenticatie. Implementeert `UserDetailsService` voor Spring Security integratie.

**`registerUser(User user)`**
- Registreert nieuwe gebruiker met beveiligde wachtwoord opslag
- Proces: 
  1. Valideer username/email uniciteit
  2. Hash wachtwoord met BCrypt (salt automatisch)
  3. Ken standaard rol toe (STUDENT)
  4. Persist naar database
- BCrypt genereert automatisch salt, geen aparte opslag nodig

**`loadUserByUsername(String username)`**
- Spring Security integratie voor authenticatie
- Converteert database User naar Spring Security UserDetails
- Role mapping: "STUDENT" wordt "ROLE_STUDENT" authority

#### OrderService.java
Service voor order processing en checkout workflow.

**`checkout(User user)`**
- `@Transactional`: Atomic operation - alles slaagt of niets
- Proces:
  1. Valideer niet-leeg winkelmandje
  2. Creëer Order entity met CONFIRMED status
  3. Converteer CartItems naar OrderItems
  4. Genereer uniek bevestigingsnummer (formaat: CONF-XXXXXXXX)
  5. Persist order
  6. Leeg winkelmandje
- Returns: Order met bevestigingsnummer

#### CartItem.java
Model class met business logic methoden.

**`getRentalDays()`**
- Berekent aantal verhuurdagen inclusief start- en einddatum
- Gebruikt: `ChronoUnit.DAYS.between(startDate, endDate) + 1`

**`getTotalPrice()`**
- Berekent totaalprijs voor dit cart item
- Formule: `pricePerDay × quantity × rentalDays`

### Repository Layer

Alle repositories extenden `JpaRepository` voor CRUD operaties.

**Custom Query Methods** (Spring Data JPA naming convention):
- `findByUsername()` - Zoekt user op basis van username
- `findByCategoryId()` - Filtert producten per categorie
- `findByAvailableTrue()` - Alleen beschikbare producten
- `searchProducts()` - Custom JPQL query voor zoekfunctionaliteit

**JPQL Query Voorbeeld** (`ProductRepository.java`):
```java
@Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List searchProducts(@Param("keyword") String keyword);
```

---

##  Frontend Implementatie

### Thymeleaf Templates

**Template Engine**: Server-side rendering met Thymeleaf  
**Styling**: Bootstrap 5 voor responsive design  
**Forms**: HTML5 validatie + server-side validatie



### User Experience Features

- **Flash Messages**: Success/Error feedback via RedirectAttributes
- **Client-side Validation**: HTML5 attributes (required, min, type)
- **Server-side Validation**: Service layer validaties
- **Responsive Design**: Mobile-friendly via Bootstrap grid system

---

##  Testing & Development

### Test Data

**Automatische Initialisatie**: `DataInitializer.java` maakt test data aan:
- 4 Categorieën
- 10 Producten verdeeld over categorieën
- 1 Test gebruiker (student/password123)

### Admin Endpoints (Development Only)

**WAARSCHUWING**: Verwijder of beveilig deze in productie!

- `GET /admin/count` - Aantal records per tabel
- `GET /admin/init-data` - Handmatig test data aanmaken
- `GET /admin/delete-all` - Verwijder alle data (GEVAARLIJK)

### Troubleshooting

**Database Connection Errors**:
```bash
# Check MySQL status
sudo service mysql status

# Start MySQL
sudo service mysql start
```

**Port Already in Use**:
Wijzig in `application.properties`: `server.port=8081`

**Lombok Not Working**:
- Installeer Lombok plugin in IntelliJ
- Enable annotation processing in settings

---

##  Bronnen & Tutorials

### Gebruikte Bronnen & Referenties

### 🔹 Officiële Documentatie

**Spring Boot Documentation**  
https://docs.spring.io/spring-boot/docs/current/reference/html/  
*Gebruikt voor algemene Spring Boot setup, application properties configuratie.*

**Spring Security Reference**  
https://docs.spring.io/spring-security/reference/index.html/  
*Gebruikt voor security configuratie, BCrypt implementatie.*

**Spring Data JPA**  
https://docs.spring.io/spring-data/jpa/docs/current/reference/html/  
*Gebruikt voor repository patterns, custom queries, entity mappings.*

**Thymeleaf Documentation**  
https://www.thymeleaf.org/documentation.html  
*Gebruikt voor template syntax, form handling, conditionals.*

**Hibernate ORM Documentation**  
https://hibernate.org/orm/documentation/  
*Gebruikt voor entity-relaties, cascade types, fetch strategies.*

### 🔹 Security & Best Practices

**OWASP Password Storage Cheat Sheet**  
https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html  
*Gebruikt voor BCrypt best practices, salt usage, veilig wachtwoordbeheer.*

**BCrypt – Wikipedia**  
https://en.wikipedia.org/wiki/Bcrypt  
*Gebruikt voor conceptueel begrip van het algoritme.*

**Spring Security BCrypt Encoder API**  
https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/bcrypt/BCryptPasswordEncoder.html  
*Gebruikt voor configuratie van BCryptPasswordEncoder.*

### 🔹 Tutorials & Guides

**Spring Boot + MySQL (Spring.io Guide)**  
https://spring.io/guides/gs/accessing-data-mysql/  
*Gebruikt voor database setup en JPA configuratie.*

**Baeldung – Spring Security met Thymeleaf**  
https://www.baeldung.com/spring-security-thymeleaf  
*Gebruikt voor login-form integratie.*

**Baeldung – Spring Boot Security Auto Configuration**  
https://www.baeldung.com/spring-boot-security-autoconfiguration  
*Gebruikt voor het begrijpen van security defaults.*

**Bootstrap 5 Documentation**  
https://getbootstrap.com/docs/5.3/  
*Gebruikt voor front-end layout, grids, formulieren, componenten.*

### 🔹 Development Tools

**Project Lombok**  
https://projectlombok.org/  
*Gebruikt voor @Data, @Getter, @Setter, @RequiredArgsConstructor.*

**MySQL via WSL (Microsoft Docs)**  
https://learn.microsoft.com/en-us/windows/wsl/tutorials/wsl-database  
*Gebruikt voor MySQL installatie & configuratie binnen WSL.*

**Maven Official Documentation**  
https://maven.apache.org/guides/  
*Gebruikt voor dependency-management en build lifecycle.*

### 🔹 Code Voorbeelden & Inspiratie

**Spring PetClinic (Sample Project)**  
https://github.com/spring-projects/spring-petclinic  
*Gebruikt voor projectstructuur en best-practice voorbeelden.*

**Spring Security Samples**  
https://github.com/spring-projects/spring-security-samples  
*Gebruikt voor configuratievoorbeelden en authentication flows.*

### 🔹 Externe Video- & AI-bronnen

**Claude AI — Project Assistance**  
https://claude.ai  
*Gebruikt voor code generatie, architecture design, debugging assistance.*

**ChatGPT — Project Assistance**  
https://chatgpt.com/share/693c38a9-d170-8007-88f8-b2dd29979a3c  
*Gebruikt voor aanvullende code voorbeelden en troubleshooting.*

**YouTube Tutorial 1 — Spring Boot Login/Register**  
https://www.youtube.com/watch?v=ig8k_5nEov4  
*Gebruikt voor begrip van Spring Security login flow.*

**YouTube Tutorial 2 — Spring Boot Security + BCrypt**  
https://www.youtube.com/watch?v=gJrjgg1KVL4  
*Gebruikt voor BCrypt implementatie voorbeelden.*


### Gebruikte Prompts

**Volledige chat geschiedenis beschikbaar op**:  
https://chatgpt.com/c/68f7a8e1-65a0-8325-ac0e-97a6d799dd2d 










