# Task Tracker – Automated Testing Framework

## Opis projekta
Ovaj projekt je izrađen u sklopu kolegija **Metode i tehnike testiranja programske podrške (MITTPP)**.  

Izrađena je jednostavna REST API aplikacija te implementiran framework za automatsko testiranje programske podrške.

Aplikacija je implementirana kao **Spring Boot REST API** koji se povezuje na **PostgreSQL** bazu podataka i omogućuje osnovno upravljanje korisnicima i zadacima.

---

## Korištene tehnologije
- **Java 25**
- **Spring Boot**
- **JdbcTemplate**
- **PostgreSQL**
- **Maven**
- **JUnit 5** – unit testiranje
- **Mockito** – mockanje ovisnosti
- **Spring Boot Test & MockMvc** – integracijsko testiranje
- **Maven Surefire & Surefire Report** – pokretanje testova i generiranje izvještaja

---

## Funkcionalnosti aplikacije
### User API
- `GET /users` – dohvat svih korisnika
- `GET /users/{id}` – dohvat korisnika po ID-u (404 ako ne postoji)
- `POST /users` – kreiranje korisnika  
  - validacija ulaza  
  - detekcija duplikata (409 Conflict)

### Task API
- `GET /tasks` – dohvat zadataka
- `GET /tasks?projectId=&status=` – filtriranje zadataka
- `POST /tasks` – kreiranje zadatka  
  - validacija podataka  
  - provjera referencijalnog integriteta

---

## Testiranje
U projektu su implementirani **različiti oblici automatskog testiranja**:

### Unit testovi
Testiraju se pojedinačne komponente izolirano, bez baze podataka i Spring konteksta.
- Validacija ulaznih podataka
- Obrada iznimki
- Mockanje repository sloja pomoću Mockito

Primjer:
- neispravan username → `IllegalArgumentException`
- duplikat korisnika → `DuplicateResourceException`

### Integracijski testovi
Testira se web sloj aplikacije (controller + exception handler) pomoću **MockMvc**.
- Provjera HTTP status kodova
- Provjera JSON odgovora
- Testiranje grešaka (404, 409)

Ukupno je implementirano **6 testnih slučajeva**, kako je zahtijevano u zadatku.

---

## Pokretanje aplikacije
Preduvjeti:
- Java 25+
- PostgreSQL
- Maven

Pokretanje aplikacije:
```bash
mvn spring-boot:run
```

Pokretanje svih testova:
```bash
mvn test
```

HTML izvještaj o izvršavanju testova generira se pomoću Maven Surefire Report plugina.

Pokretanje:
```bash
mvn clean test surefire-report:report
```

Izvještaj se generira na lokaciji:
```bash
target/site/surefire-report.html
```

## Autor
Srđan Lazić

Diplomski studij Računarstvo, Podatkovne znanosti, FERIT
