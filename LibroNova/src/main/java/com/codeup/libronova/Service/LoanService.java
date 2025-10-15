/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codeup.libronova.Service;

import com.codeup.libronova.domain.Loan;
import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.exception.PersistenceException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public interface LoanService {
    
     void lendBook(Loan loan) throws BusinessException;

    double returnBook(int loanId, LocalDate returnDate) throws BusinessException;

    Optional<Loan> findById(int id) throws BusinessException;

    List<Loan> findAll() throws BusinessException;

    List<Loan> findOverdueLoans() throws BusinessException;

    void delete(int id) throws BusinessException;
    
     void deleteById(int id) throws PersistenceException;
     
     void save(Loan loan) throws PersistenceException;
}

