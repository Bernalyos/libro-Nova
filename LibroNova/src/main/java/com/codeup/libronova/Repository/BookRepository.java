/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codeup.libronova.Repository;

import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public interface BookRepository {
    
     // CREATE (Generalmente devuelve el objeto guardado)
    Book save(Book book) throws PersistenceException;

    // UPDATE (El libro debe existir antes de llamar a este método)
    Book update(Book book) throws PersistenceException;

    // READ / FIND
    Optional<Book> findByIsbn(String isbn) throws PersistenceException;

    List<Book> findAll() throws PersistenceException;

    List<Book> searchBooks(String searchTerm) throws PersistenceException;
    
    // DELETE (Por la clave primaria ISBN)
    void deleteByIsbn(String isbn) throws PersistenceException;
    
    // VALIDATION
    boolean existsByIsbn(String isbn) throws PersistenceException;
}
