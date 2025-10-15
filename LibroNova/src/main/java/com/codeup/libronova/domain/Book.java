
package com.codeup.libronova.domain;

import java.math.BigDecimal;

import java.time.OffsetDateTime;

/**
 *
 * @author Coder
 */
public class Book {
    
    // Identificadores (Usando Long para IDs auto-incrementales es estándar)
    private Long id;
    private String isbn; // Clave de negocio (VARCHAR 13)

    // Core Data
    private String title;
    private String author;
    private String category;
    private BigDecimal referencePrice;

    // Inventory
    private int totalCopies;
    private int availableCopies;

    // Status and Audit
    private boolean isActive;
    private OffsetDateTime createdAt;

    // --- CONSTRUCTORES ---
    
    // 1. Constructor vacío (requerido por muchos frameworks ORM como JPA)
    public Book() {}

    // 2. Constructor Completo (para inicializar desde la base de datos)
    public Book(Long id, String isbn, String title, String author, String category, 
                int totalCopies, int availableCopies, BigDecimal referencePrice, 
                boolean isActive, OffsetDateTime createdAt) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.referencePrice = referencePrice;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

   
    
    // --- GETTERS y SETTERS ---

    public Long getId () { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    // Corregidos: Getter y Setter para Category
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    // Corregidos: Getter y Setter para Total Copies
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    // Corregidos: Getter y Setter para Available Copies
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    // Corregidos: Getter y Setter para Reference Price
    public BigDecimal getReferencePrice() { return referencePrice; }
    public void setReferencePrice(BigDecimal referencePrice) { this.referencePrice = referencePrice; }

    // Corregidos: Getter y Setter para Is Active
    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    // Corregidos: Usando OffsetDateTime y su Getter/Setter
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt( OffsetDateTime createdAt) { this.createdAt = createdAt; }

    // --- toString() ---
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", referencePrice=" + referencePrice +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
}

}

