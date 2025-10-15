
package com.codeup.libronova.Service;

import com.codeup.libronova.Repository.LoanRepository;
import com.codeup.libronova.domain.Loan;
import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.exception.PersistenceException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public class LoanServiceImpl implements LoanService {
 
    private final LoanRepository loanRepository;

    private static final double DAILY_FINE_AMOUNT = 0.50; 

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public LoanServiceImpl() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void lendBook(Loan loan) throws BusinessException {
        try {
            loanRepository.save(loan);
        } catch (PersistenceException e) {
            throw new BusinessException("Failed to process loan: " + e.getMessage(), e);
        }
    }

    @Override
    public double returnBook(int loanId, LocalDate returnDate) throws BusinessException {
        try {
            Optional<Loan> optionalLoan = loanRepository.findById(loanId);
            
            if (!optionalLoan.isPresent()) {
                throw new BusinessException("Loan with ID " + loanId + " not found.");
            }
            
            Loan loan = optionalLoan.get();

            if (loan.getReturnDate()) {
                throw new BusinessException("Loan with ID " + loanId + " has already been returned.");
            }
            
            double fineAmount = calculateFine(loan.getDateDue(), returnDate);
            
            loanRepository.markAsReturned(loanId); 
            
            return fineAmount;

        } catch (PersistenceException e) {
            throw new BusinessException("Failed to process book return and update stock.", e);
        } catch (SQLException ex) {
            System.getLogger(LoanServiceImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return 0;
    }

    private double calculateFine(LocalDate dateDue, LocalDate returnDate) {
        if (returnDate.isAfter(dateDue)) {
            long daysOverdue = ChronoUnit.DAYS.between(dateDue, returnDate);
            return daysOverdue * DAILY_FINE_AMOUNT;
        }
        return 0.0;
    }

    @Override
    public Optional<Loan> findById(int id) throws BusinessException {
        try {
            return loanRepository.findById(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Error finding loan by ID: " + id, e);
        }
    }

    @Override
    public List<Loan> findAll() throws BusinessException {
        try {
            return loanRepository.findAll();
        } catch (PersistenceException e) {
            throw new BusinessException("Error retrieving all loans.", e);
        }
    }

    @Override
    public List<Loan> findOverdueLoans() throws BusinessException {
        try {
            return loanRepository.findOverdueLoans();
        } catch (PersistenceException e) {
            throw new BusinessException("Error retrieving overdue loans.", e);
        }
    }
    
    @Override
    public void delete(int id) throws BusinessException {
        try {
            loanRepository.delete(id);
        } catch (PersistenceException e) {
            throw new BusinessException("Error deleting loan with ID: " + id, e);
        }
    }
    
    @Override
    public void deleteById(int id) throws PersistenceException {
    loanRepository.deleteById(id);
}

    @Override
    public void save(Loan loan) throws PersistenceException {
    loanRepository.save(loan);
}
}

    

