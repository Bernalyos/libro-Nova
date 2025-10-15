
package com.codeup.libronova.Repository;

import com.codeup.libronova.domain.Loan;
import com.codeup.libronova.exception.PersistenceException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public interface LoanRepository {
    
     void save(Loan loan) throws PersistenceException;

    void update(Loan loan) throws PersistenceException;

    void delete(int id) throws PersistenceException;

    Optional<Loan> findById(int id) throws PersistenceException;

    List<Loan> findAll() throws PersistenceException;

    List<Loan> findOverdueLoans() throws PersistenceException;

    void markAsReturned(int id) throws SQLException;
    
     void deleteById(int id) throws PersistenceException;
     
     
}

