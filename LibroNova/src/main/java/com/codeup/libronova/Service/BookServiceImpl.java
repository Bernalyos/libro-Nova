/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codeup.libronova.Service;

import com.codeup.libronova.Repository.BookRepository;
import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.exception.PersistenceException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public class BookServiceImpl implements BookService {
    


    // Dependencia del repositorio (capa de acceso a datos)
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookServiceImpl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Book save(Book book) throws BusinessException {
        try {
            // Aquí iría la lógica de negocio antes de guardar (ej: validaciones de ISBN)
            return bookRepository.save(book);
        } catch (PersistenceException e) {
            // Manejo de error de la capa de persistencia
            throw new BusinessException("Failed to save book: " + e.getMessage(), e);
        }
    }

    @Override
    public Book update(Book book) throws BusinessException {
        try {
            // Aquí iría la lógica de negocio antes de actualizar
            return bookRepository.update(book);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to update book: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) throws BusinessException {
        try {
            return bookRepository.findByIsbn(isbn);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to find book by ISBN: " + isbn, e);
        }
    }

    @Override
    public List<Book> findAll() throws BusinessException {
        try {
            // El servicio solo recupera los datos
            return bookRepository.findAll();
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to retrieve all books.", e);
        }
    }
    
    @Override
    public List<Book> searchBooks(String searchTerm) throws BusinessException {
        try {
            return bookRepository.searchBooks(searchTerm);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to search books with term: " + searchTerm, e);
        }
    }

    @Override
    public void deleteByIsbn(String isbn) throws BusinessException {
        try {
            // Aquí iría la lógica de negocio (ej: verificar que no tiene préstamos activos)
            bookRepository.deleteByIsbn(isbn);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to delete book: " + e.getMessage(), e);
        }
    }
    
}

    
