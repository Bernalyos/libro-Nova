
package com.codeup.libronova.Repository;


import com.codeup.libronova.domain.Book;
import com.codeup.libronova.exception.PersistenceException;
import com.codeup.libronova.Confing.ConnectinFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

/**
 *
 * @author Coder
 */
public class BookRepositoryImpl implements BookRepository {

    private final DataSource dataSource;

    // Constructor principal para inyección de DataSource
    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Constructor por defecto: inicializa el DataSource usando ConnectinFactory
    public BookRepositoryImpl() {
        this.dataSource = ConnectinFactory.getDataSource();
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setIsbn(rs.getString("isbn"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setCategory(rs.getString("category"));
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        book.setReferencePrice(rs.getBigDecimal("reference_price"));
        book.setIsActive(rs.getBoolean("is_active"));
        book.setCreatedAt(rs.getObject("created_at", java.time.OffsetDateTime.class)); 
        return book;
    }

    @Override
    public Book save(Book book) throws PersistenceException {
        String SQL_INSERT = "INSERT INTO book (isbn, title, author, category, total_copies, available_copies, reference_price, is_active, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getCategory());
            ps.setInt(5, book.getTotalCopies());
            ps.setInt(6, book.getAvailableCopies());
            ps.setBigDecimal(7, book.getReferencePrice());
            ps.setBoolean(8, book.getIsActive());
            ps.setObject(9, book.getCreatedAt());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting book failed, no rows affected.");
            }
            return book;
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) { 
                 throw new PersistenceException("ISBN " + book.getIsbn() + " already exists (Constraint violation).", e);
            }
            throw new PersistenceException("Error saving book: " + e.getMessage(), e);
        }
    }

    @Override
    public Book update(Book book) throws PersistenceException {
        String SQL_UPDATE = "UPDATE book SET title=?, author=?, category=?, total_copies=?, available_copies=?, reference_price=?, is_active=? WHERE isbn=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getAvailableCopies());
            ps.setBigDecimal(6, book.getReferencePrice());
            ps.setBoolean(7, book.getIsActive());
            ps.setString(8, book.getIsbn());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new PersistenceException("Book with ISBN " + book.getIsbn() + " not found for update.");
            }
            return book;
        } catch (SQLException e) {
            throw new PersistenceException("Error updating book with ISBN " + book.getIsbn() + ": " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) throws PersistenceException {
        String SQL = "SELECT * FROM book WHERE isbn = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToBook(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error finding book by ISBN: " + isbn, e);
        }
    }

    @Override
    public List<Book> findAll() throws PersistenceException {
        String SQL = "SELECT * FROM book ORDER BY title ASC";
        List<Book> books = new ArrayList<>();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                books.add(mapResultSetToBook(rs));
            }
            return books;
        } catch (SQLException e) {
            throw new PersistenceException("Error retrieving all books.", e);
        }
    }

    @Override
    public List<Book> searchBooks(String searchTerm) throws PersistenceException {
        String SQL = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ? OR category LIKE ? ORDER BY title ASC";
        List<Book> books = new ArrayList<>();
        String searchPattern = "%" + searchTerm.trim() + "%";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    books.add(mapResultSetToBook(rs));
                }
                return books;
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error searching books with term: " + searchTerm, e);
        }
    }

    @Override
    public void deleteByIsbn(String isbn) throws PersistenceException {
        String SQL = "DELETE FROM book WHERE isbn = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, isbn);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new PersistenceException("Book with ISBN " + isbn + " not found for deletion.");
            }
        } catch (PersistenceException e) {
            throw e; 
        } catch (SQLException e) {
            throw new PersistenceException("Error deleting book with ISBN " + isbn + ": " + e.getMessage(), e);
        }
    }

    @Override
    public boolean existsByIsbn(String isbn) throws PersistenceException {
        String SQL = "SELECT COUNT(*) FROM book WHERE isbn = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error checking existence for ISBN: " + isbn, e);
        }
    }
}