/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.libronova.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Coder
 */
public class Loan {

    private int id;
    private int memberId;
    private int bookId;
    private LocalDate dateLoaned;
    private LocalDate dateDue;
    private boolean returned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

   

    public Loan(int id, int memberId, int bookId, LocalDate dateLoaned, LocalDate dateDue, boolean returned) {
        this.id = id;
        this.memberId = memberId;
        this.bookId = bookId;
        this.dateLoaned = dateLoaned;
        this.dateDue = dateDue;
        this.returned = returned;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public LocalDate getDateLoaned() { return dateLoaned; }
    public void setDateLoaned(LocalDate dateLoaned) { this.dateLoaned = dateLoaned; }

    public LocalDate getDateDue() { return dateDue; }
    public void setDateDue(LocalDate dateDue) { this.dateDue = dateDue; }

    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

     @Override
     public String toString() {
        return "Loan{id=%s, memberId=%s, bookId=%s, dateLoaned=%s, dateDue=%s, returned=%s, createdAt=%s, updatedAt=%s}"
            .formatted(id, memberId, bookId, dateLoaned, dateDue, returned, createdAt, updatedAt);
    }

}
