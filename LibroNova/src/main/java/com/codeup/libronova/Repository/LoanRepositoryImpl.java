
package com.codeup.libronova.Repository;

import com.codeup.libronova.Confing.ConnectinFactory;
import com.codeup.libronova.domain.Loan;
import com.codeup.libronova.exception.PersistenceException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author yoshi
 */
public class LoanRepositoryImpl implements LoanRepository {
    
     @Override
    public void save(Loan loan) throws PersistenceException {
        String sqlInsert = "INSERT INTO loan (member_id, book_id, date_loaned, date_due, returned, created_at, updated_at) VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
        String sqlUpdateStock = "UPDATE book SET stock = stock - 1 WHERE id=? AND stock > 0";

        try (Connection conn = ConnectinFactory.getConnection()) {
            conn.setAutoCommit(false); // Begin transaction

            try (PreparedStatement psStock = conn.prepareStatement(sqlUpdateStock); PreparedStatement psLoan = conn.prepareStatement(sqlInsert)) {

                // Decrease book stock
                psStock.setInt(1, loan.getBookId());
                int stockUpdated = psStock.executeUpdate();
                if (stockUpdated == 0) {
                    conn.rollback();
                    throw new PersistenceException("Book not available or out of stock.");
                }

                // Insert loan record
                psLoan.setInt(1, loan.getMemberId());
                psLoan.setInt(2, loan.getBookId());
                psLoan.setDate(3, Date.valueOf(loan.getLoanDate()));
                psLoan.setDate(4, Date.valueOf(loan.getDateDue()));
                psLoan.setBoolean(5, loan.getReturnDate());
                psLoan.executeUpdate();

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback();
                throw new PersistenceException("Error saving loan: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new PersistenceException("Database error while saving loan: " + e.getMessage());
        }
    }

    @Override
    public void update(Loan loan) throws PersistenceException {
        String sql = "UPDATE loan SET member_id=?, book_id=?, date_loaned=?, date_due=?, returned=?, updated_at=NOW() WHERE id=?";
        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, loan.getMemberId());
            ps.setInt(2, loan.getBookId());
            ps.setDate(3, Date.valueOf(loan.getLoanDate()));
            ps.setDate(4, Date.valueOf(loan.getDateDue()));
            ps.setBoolean(5, loan.getReturnDate());
            ps.setInt(6, loan.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error updating loan: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws PersistenceException {
        String sql = "DELETE FROM loan WHERE id=?";
        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException("Error deleting loan: " + e.getMessage());
        }
    }

    @Override
    public Optional<Loan> findById(int id) throws PersistenceException {
        String sql = "SELECT * FROM loan WHERE id=?";
        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Loan loan = mapToLoan(rs);
                return Optional.of(loan);
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error finding loan by ID: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findAll() throws PersistenceException {
        List<Loan> loans = new ArrayList<>();
        String sql = "SELECT * FROM loan";
        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                loans.add(mapToLoan(rs));
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error listing loans: " + e.getMessage());
        }
        return loans;
    }

    @Override
    public List<Loan> findOverdueLoans() throws PersistenceException {
        List<Loan> overdueLoans = new ArrayList<>();
        String sql = "SELECT * FROM loan WHERE returned=FALSE AND date_due < CURDATE()";

        try (Connection conn = ConnectinFactory.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                overdueLoans.add(mapToLoan(rs));
            }

        } catch (SQLException e) {
            throw new PersistenceException("Error finding overdue loans: " + e.getMessage());
        }
        return overdueLoans;
    }

    @Override
    public void markAsReturned(int id) throws SQLException {
        String updateLoan = "UPDATE loan SET returned=TRUE, updated_at=NOW() WHERE id=?";
        String increaseStock = "UPDATE book SET stock = stock + 1 WHERE id=(SELECT book_id FROM loan WHERE id=?)";

        try (Connection conn = ConnectinFactory.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psLoan = conn.prepareStatement(updateLoan); PreparedStatement psBook = conn.prepareStatement(increaseStock)) {

                psLoan.setInt(1, id);
                psLoan.executeUpdate();

                psBook.setInt(1, id);
                psBook.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new SQLException("Error marking loan as returned: " + e.getMessage(), e);
            } finally {
                conn.setAutoCommit(true);
            }

        }
    }

    // --- Helper method ---
    private Loan mapToLoan(ResultSet rs) throws SQLException {
        Loan loan = new Loan();
        loan.setId(rs.getInt("id"));
        loan.setMemberId(rs.getInt("member_id"));
        loan.setBookId(rs.getInt("book_id"));
        loan.setLoanDate(rs.getDate("date_loaned").toLocalDate());
        loan.setDateDue(rs.getDate("date_due").toLocalDate());
        loan.setReturnDate(rs.getBoolean("returned"));
        loan.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        loan.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return loan;
    }
    
@Override
public void deleteById(int id) throws PersistenceException {
    String sql = "DELETE FROM loans WHERE id = ?";
    try (Connection conn = ConnectinFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
    } catch (SQLException e) {
        throw new PersistenceException("Error al eliminar préstamo", e);
    }
}}


