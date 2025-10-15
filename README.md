# LibroNova

## General Description
LibroNova is a library network management system developed in Java SE. It centralizes the administration of books, members, users, and loans. The system solves problems of duplication, inconsistencies, and data loss, offering a simple graphical interface based on JOptionPane and JDBC persistence.

## Main Features
- **Book Management:** Register, edit, activate/deactivate, filter by category/author, and validate unique ISBNs.
- **User Management & Authentication:** Login with roles (ADMIN/ASSISTANT), user registration with default properties, and log registration simulating HTTP calls.
- **Member Management:** Register, edit, and validate active status.
- **Loan Management:** Register and return loans with JDBC transactions, stock control, and fine calculation.
- **Export & Files:** Export data to CSV, read configuration from `config.properties`, and log activity in `app.log`.
- **Exceptions & Validations:** Error handling with custom exceptions and business rule validations.
- **Unit Testing:** Business rule and service validation with JUnit 5.

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- MySQL or PostgreSQL

## Installation & Execution
1. Clone the repository:
   ```bash
   git clone https://github.com/Bernalyos/libro-Nova.git
   ```
2. Configure the database and the `config.properties` file:
   ```properties
   db.url=jdbc:mysql://localhost:3306/libronova
   db.user=root
   db.password=****
   diasPrestamo=7
   multaPorDia=1500
   ```
3. Build the project:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass=com.codeup.libronova.LibroNova
   ```

## Project Structure
```
LibroNova/
├── src/main/java/com/codeup/libronova/
│   ├── domain/        # Models: Book, Loan, Member, User
│   ├── repository/    # DAOs and JDBC implementations
│   ├── service/       # Services and business logic
│   ├── exception/     # Custom exceptions
│   ├── UI/            # Graphical interface (JOptionPane)
│   └── Confing/       # JDBC connection
├── src/main/resources/
│   └── config.properties
├── src/test/java/     # Unit tests (JUnit 5)
├── pom.xml            # Maven configuration
└── README.md
```

## Screenshots
> Add images of the JOptionPane interface and examples of listings/tables here.

## Diagrams
### Class Diagram
> Include the UML diagram of the main classes and relationships here.

### Use Case Diagram
> Include the use case diagram for the actors: Administrator, Assistant, Member.

## Exports & Logs
- **CSV:**
  - `libros_export.csv`: Complete book catalog.
  - `prestamos_vencidos.csv`: Overdue loans.
- **Logs:**
  - `app.log`: Activity and error log.

## Coder Information
- **Name:** [Your Name]
- **Clan:** [Your Clan]
- **Email:** [Your Email]
- **Document:** [Your Document]

---
Thank you for using LibroNova! For questions or improvements, open an issue in the repository.
