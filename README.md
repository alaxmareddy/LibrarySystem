# LibrarySystem

Test Example for Library Demo

✅ Summary of What We’ll Provide

Java 17 + Spring Boot project

REST API for:

Registering borrowers

Registering books

Listing all books

Borrowing a book

Returning a book

H2 in-memory DB (easy to set up for a demo)

Error handling and basic validation

Documentation of assumptions

Simple clean code

README content

📁 Sample Project Structure
library-api/
├── src/
│   └── main/
│       ├── java/com/library/
│       │   ├── controller/
│       │   ├── model/
│       │   ├── repository/
│       │   ├── service/
│       │   └── LibraryApiApplication.java
│       └── resources/
│           ├── application.properties
│           └── data.sql (optional seed data)
├── README.md
└── pom.xml

📌 Key Assumptions

Same ISBN = same book content (title + author).

Each book copy is treated as a separate book (with unique ID).

Only one borrower can borrow a book (book ID) at a time.

No authentication needed for the demo.

Use of H2 in-memory database for simplicity and speed in interviews.

⚙️ Tech Stack

Java 17

Spring Boot (REST API)

H2 Database (in-memory)

Maven (dependency manager)

Lombok (optional, to reduce boilerplate)

🧠 Data Models
// Borrower.java
@Entity
public class Borrower {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
}

// Book.java
@Entity
public class Book {
    @Id @GeneratedValue
    private Long id;
    private String isbn;
    private String title;
    private String author;

    @ManyToOne
    private Borrower borrowedBy; // null if not borrowed
}

📨 API Endpoints
1. Register a new borrower
POST /api/borrowers
Body:
{
  "name": "Alice",
  "email": "alice@example.com"
}

2. Register a new book
POST /api/books
Body:
{
  "isbn": "123-ABC",
  "title": "Spring in Action",
  "author": "Craig Walls"
}

3. List all books
GET /api/books

4. Borrow a book (by book ID)
POST /api/borrowers/{borrowerId}/borrow/{bookId}

5. Return a book (by book ID)
POST /api/borrowers/{borrowerId}/return/{bookId}

📂 application.properties
spring.datasource.url=jdbc:h2:mem:librarydb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update

📝 README.md (content)
# 📚 Library REST API (Demo)

This is a simple Library Management API built using Java 17 and Spring Boot for interview/demo purposes.

## ✅ Features

- Register borrowers
- Register books (multiple copies allowed)
- List all books
- Borrow a book
- Return a book

## 🧰 Tech Stack

- Java 17
- Spring Boot
- H2 (in-memory)
- Maven

## 🚀 How to Run

```bash
mvn spring-boot:run


Access H2 console at: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:librarydb

📮 API Endpoints

See API Endpoints section in documentation above.

🧠 Assumptions

Same ISBN = same book title & author

Each book copy is unique (has its own ID)

A book (by ID) can be borrowed by only one borrower at a time

No authentication required

🧪 Tests (optional)

Add unit tests using Spring Boot's @WebMvcTest and MockMvc

🐳 Docker (optional)

FROM openjdk:17
COPY target/library-api.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
