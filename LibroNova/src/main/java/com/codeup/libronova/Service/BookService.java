/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codeup.libronova.Service;

import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.BusinessException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public interface BookService {
     
     // Métodos CRUD básicos delegados al repositorio
   Book save(Book book) throws BusinessException;

    Book update(Book book) throws BusinessException;

    Optional<Book> findByIsbn(String isbn) throws BusinessException;

    List<Book> findAll() throws BusinessException;

    List<Book> searchBooks(String searchTerm) throws BusinessException;

    void deleteByIsbn(String isbn) throws BusinessException;


}
    

