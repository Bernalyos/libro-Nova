
package com.codeup.libronova.UI;


import com.codeup.libronova.Service.BookService;
import com.codeup.libronova.Service.LoanService;
import com.codeup.libronova.Service.MemberService;
import com.codeup.libronova.domain.Book;
import com.codeup.libronova.domain.Loan;
import com.codeup.libronova.domain.Member;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.codeup.libronova.exception.BusinessException;
import com.codeup.libronova.exception.PersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AppUI {

    private final BookService bookService;
    private final MemberService memberService;
    private final LoanService loanService;

    public AppUI(BookService bookService, MemberService memberService, LoanService loanService) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.loanService = loanService;
    }

    public void start() throws IOException {
        mainMenu();
    }

    private void mainMenu() throws IOException {
        Object[] options = {"Catálogo", "Socios", "Préstamos", "Exportaciones", "Usuarios", "Salir"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                null,
                "Seleccione una opción del sistema:",
                "Menú Principal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            switch (choice) {
                case 0:
                    bookMenu();
                    break;
                case 1:
                    memberMenu();
                    break;
                case 2:
                    loanMenu();
                    break;
                case 3:
                    exportMenu();
                    break;
                case 4:
                    userMenu();
                    break;
                case 5:
                case JOptionPane.CLOSED_OPTION:
                    JOptionPaneHelper.info("Sistema LibroNova cerrado.");
                    break;
            }
        } while (choice != 5 && choice != JOptionPane.CLOSED_OPTION);
    }

    // ================== LIBROS ==================
    private void bookMenu() {
        Object[] options = {"1. Listar Todos", "2. Buscar por ISBN", "3. Añadir Libro", "4. Modificar Libro", "5. Eliminar Libro", "6. Volver"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                null,
                "Seleccione una operación de Catálogo:",
                "Gestión de Catálogo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            try {
                switch (choice) {
                    case 0:
                        listAllBooks();
                        break;
                    case 1:
                        findBookByIsbn();
                        break;
                    case 2:
                        addBook();
                        break;
                    case 3:
                        JOptionPaneHelper.info("Función de modificar libro no implementada en UI.");
                        break;
                    case 4:
                        deleteBook();
                        break;
                    case 5:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                }
            } catch (BusinessException e) {
                JOptionPaneHelper.error("Error de Negocio: " + e.getMessage());
            } catch (Exception e) {
                JOptionPaneHelper.error("Error inesperado: " + e.getMessage());
            }
        } while (choice != 5 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void listAllBooks() throws BusinessException {
        List<Book> books = bookService.findAll();
        if (books.isEmpty()) {
            JOptionPaneHelper.info("No hay libros registrados.");
            return;
        }
        StringBuilder sb = new StringBuilder("Listado de Libros:\n");
        for (Book b : books) {
            sb.append("ID: ").append(b.getId())
              .append(" | ISBN: ").append(b.getIsbn())
              .append(" | Título: ").append(b.getTitle())
              .append(" | Autor: ").append(b.getAuthor())
              .append(" | Categoría: ").append(b.getCategory())
              .append(" | Disponibles: ").append(b.getAvailableCopies())
              .append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Libros", JOptionPane.INFORMATION_MESSAGE);
    }

    private void findBookByIsbn() throws BusinessException {
        String isbn = JOptionPane.showInputDialog(null, "Ingrese el ISBN del libro a buscar:", "Buscar Libro", JOptionPane.QUESTION_MESSAGE);
        if (isbn == null || isbn.trim().isEmpty()) {
            return;
        }
        Optional<Book> bookOpt = bookService.findByIsbn(isbn.trim());
        if (bookOpt.isPresent()) {
            Book b = bookOpt.get();
            String info = "ID: " + b.getId()
                        + "\nISBN: " + b.getIsbn()
                        + "\nTítulo: " + b.getTitle()
                        + "\nAutor: " + b.getAuthor()
                        + "\nCategoría: " + b.getCategory()
                        + "\nDisponibles: " + b.getAvailableCopies();
            JOptionPaneHelper.info(info);
        } else {
            JOptionPaneHelper.info("Libro no encontrado.");
        }
    }

    private void addBook() {
        try {
            String isbn = JOptionPane.showInputDialog("ISBN:");
            if (isbn == null || isbn.trim().isEmpty()) return;
            String title = JOptionPane.showInputDialog("Título:");
            if (title == null || title.trim().isEmpty()) return;
            String author = JOptionPane.showInputDialog("Autor:");
            if (author == null || author.trim().isEmpty()) return;
            String category = JOptionPane.showInputDialog("Categoría:");
            if (category == null) category = "";
            int totalCopies = Integer.parseInt(JOptionPane.showInputDialog("Copias totales:"));
            int availableCopies = Integer.parseInt(JOptionPane.showInputDialog("Copias disponibles:"));
            String priceStr = JOptionPane.showInputDialog("Precio de referencia:");
            java.math.BigDecimal referencePrice = new java.math.BigDecimal(priceStr);

            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setAuthor(author);
            book.setCategory(category);
            book.setTotalCopies(totalCopies);
            book.setAvailableCopies(availableCopies);
            book.setReferencePrice(referencePrice);
            book.setIsActive(true);
            book.setCreatedAt(java.time.OffsetDateTime.now());

            bookService.save(book);
            JOptionPaneHelper.info("Libro agregado correctamente.");
        } catch (Exception e) {
            JOptionPaneHelper.error("Error al agregar libro: " + e.getMessage());
        }
    }

    private void deleteBook() throws BusinessException {
        String isbn = JOptionPane.showInputDialog(null, "Ingrese el ISBN del libro a ELIMINAR:", "Eliminar Libro", JOptionPane.WARNING_MESSAGE);
        if (isbn == null || isbn.trim().isEmpty()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el libro con ISBN: " + isbn + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            bookService.deleteByIsbn(isbn.trim());
            JOptionPaneHelper.info("Libro eliminado con éxito.");
        }
    }

    // ================== MIEMBROS ==================
    private void memberMenu() {
        Object[] options = {"1. Listar Todos", "2. Listar Activos", "3. Buscar por ID", "4. Dar de Baja (Eliminar Lógico)", "5. Volver"};
        int choice;

        do {
            choice = JOptionPaneHelper.showMenu(options, "Gestión de Socios", "Seleccione una operación para Socios:");

            try {
                switch (choice) {
                    case 0:
                        listAllMembers();
                        break;
                    case 1:
                        listActiveMembers();
                        break;
                    case 2:
                        findMemberById();
                        break;
                    case 3:
                        deleteMember();
                        break;
                    case 4:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                }
            } catch (BusinessException e) {
                JOptionPaneHelper.showError("Error de Negocio: " + e.getMessage());
            } catch (Exception e) {
                JOptionPaneHelper.showError("Error inesperado: " + e.getMessage());
            }
        } while (choice != 4 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void listAllMembers() throws BusinessException {
        List<Member> members = memberService.findAll();
        StringBuilder sb = new StringBuilder("Listado de Miembros:\n");
        for (Member m : members) {
            sb.append("ID: ").append(m.getId())
              .append(" | Nombre: ").append(m.getName())
              .append(" | Activo: ").append(m.isActive())
              .append(" | Rol: ").append(m.getRole())
              .append(" | Nivel: ").append(m.getAccessLevel())
              .append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Miembros", JOptionPane.INFORMATION_MESSAGE);
    }

    private void listActiveMembers() throws BusinessException {
        List<Member> members = memberService.findActiveMembers();
        StringBuilder sb = new StringBuilder("Listado de Miembros Activos:\n");
        for (Member m : members) {
            sb.append("ID: ").append(m.getId())
              .append(" | Nombre: ").append(m.getName())
              .append(" | Rol: ").append(m.getRole())
              .append(" | Nivel: ").append(m.getAccessLevel())
              .append("\n");
        }
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));
        JOptionPane.showMessageDialog(null, scrollPane, "Miembros Activos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void findMemberById() throws BusinessException {
        String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del socio a buscar:", "Buscar Socio", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            Optional<Member> memberOpt = memberService.findById(id);
            if (memberOpt.isPresent()) {
                Member member = memberOpt.get();
                String info = String.format("ID: %d\nNombre: %s\nRol: %s\nEstado: %s",
                        member.getId(), member.getName(), member.getRole().name(), member.isActive() ? "ACTIVO" : "INACTIVO");
                JOptionPaneHelper.info(info);
            } else {
                JOptionPaneHelper.info("Socio no encontrado con ID: " + id);
            }
        } catch (NumberFormatException e) {
            JOptionPaneHelper.error("ID debe ser un número entero.");
        }
    }

    private void deleteMember() throws BusinessException {
        String idStr = JOptionPane.showInputDialog(null, "Ingrese el ID del socio a DAR DE BAJA (Eliminar Lógico):", "Dar de Baja Socio", JOptionPane.WARNING_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) {
            return;
        }
        try {
            int id = Integer.parseInt(idStr);
            if (JOptionPane.showConfirmDialog(null, "¿Está seguro de DAR DE BAJA al socio con ID: " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                memberService.deleteById(id);
                JOptionPaneHelper.info("Socio dado de baja (eliminación lógica) con éxito.");
            }
        } catch (NumberFormatException e) {
            JOptionPaneHelper.error("ID debe ser un número entero.");
        } catch (PersistenceException ex) {
            Logger.getLogger(AppUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ================== PRÉSTAMOS ==================
    private void loanMenu() {
        Object[] options = {"1. Listar Préstamos", "2. Registrar Préstamo", "3. Buscar Préstamo", "4. Eliminar Préstamo", "5. Exportar a CSV", "6. Volver"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                null,
                "Seleccione una operación de Préstamo:",
                "Gestión de Préstamos",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            try {
                switch (choice) {
                    case 0:
                        listLoans();
                        break;
                    case 1:
                        addLoan();
                        break;
                    case 2:
                        searchLoan();
                        break;
                    case 3:
                        deleteLoan();
                        break;
                    case 4:
                        exportLoansToCSV();
                        break;
                    case 5:
                        return;
                    case JOptionPane.CLOSED_OPTION:
                        return;
                }
            } catch (Exception e) {
                JOptionPaneHelper.error("Error inesperado: " + e.getMessage());
            }
        } while (choice != 5 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void listLoans() {
        try {
            List<Loan> loans = loanService.findAll();
            if (loans.isEmpty()) {
                JOptionPaneHelper.info("No hay préstamos registrados.");
                return;
            }
            StringBuilder sb = new StringBuilder("Listado de Préstamos:\n");
            for (Loan l : loans) {
                sb.append("ID: ").append(l.getId())
                  .append(" | Libro ID: ").append(l.getBookId())
                  .append(" | Miembro ID: ").append(l.getMemberId())
                  .append(" | Fecha préstamo: ").append(l.getLoanDate())
                  .append(" | Fecha devolución: ").append(l.getDateDue())
                  .append(" | Devuelto: ").append(l.getReturnDate())
                  .append("\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(600, 300));
            JOptionPane.showMessageDialog(null, scrollPane, "Préstamos", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPaneHelper.error("Error al listar préstamos: " + e.getMessage());
        }
    }

    private void addLoan() {
        try {
            String bookIdStr = JOptionPane.showInputDialog("ID del libro:");
            if (bookIdStr == null || bookIdStr.trim().isEmpty()) return;
            int bookId = Integer.parseInt(bookIdStr.trim());

            String memberIdStr = JOptionPane.showInputDialog("ID del miembro:");
            if (memberIdStr == null || memberIdStr.trim().isEmpty()) return;
            int memberId = Integer.parseInt(memberIdStr.trim());

            String dateDueStr = JOptionPane.showInputDialog("Fecha de devolución (YYYY-MM-DD):");
            LocalDate dateDue = null;
            if (dateDueStr != null && !dateDueStr.trim().isEmpty()) {
                dateDue = LocalDate.parse(dateDueStr.trim());
            }

            Loan loan = new Loan();
            loan.setBookId(bookId);
            loan.setMemberId(memberId);
            loan.setLoanDate(LocalDate.now());
            loan.setDateDue(dateDue);
            loan.setReturnDate(false); // No devuelto aún

            loanService.save(loan);
            JOptionPaneHelper.info("Préstamo registrado correctamente.");
        } catch (Exception e) {
            JOptionPaneHelper.error("Error al registrar préstamo: " + e.getMessage());
        }
    }

    private void searchLoan() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del préstamo a buscar:");
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            Optional<Loan> loanOpt = loanService.findById(id);
            if (loanOpt.isPresent()) {
                Loan l = loanOpt.get();
                String info = "ID: " + l.getId()
                        + "\nLibro ID: " + l.getBookId()
                        + "\nMiembro ID: " + l.getMemberId()
                        + "\nFecha préstamo: " + l.getLoanDate()
                        + "\nFecha devolución: " + l.getDateDue()
                        + "\nDevuelto: " + l.getReturnDate();
                JOptionPaneHelper.info(info);
            } else {
                JOptionPaneHelper.info("Préstamo no encontrado.");
            }
        } catch (Exception e) {
            JOptionPaneHelper.error("Error al buscar préstamo: " + e.getMessage());
        }
    }

    private void deleteLoan() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID del préstamo a eliminar:");
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            loanService.deleteById(id);
            JOptionPaneHelper.info("Préstamo eliminado correctamente.");
        } catch (Exception e) {
            JOptionPaneHelper.error("Error al eliminar préstamo: " + e.getMessage());
        }
    }

    private void exportLoansToCSV() {
        try {
            List<Loan> loans = loanService.findAll();
            if (loans.isEmpty()) {
                JOptionPaneHelper.info("No hay préstamos para exportar.");
                return;
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar préstamos como CSV");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(fileToSave)) {
                    writer.write("ID,LibroID,MiembroID,FechaPrestamo,FechaDevolucion,Devuelto\n");
                    for (Loan l : loans) {
                        writer.write(String.format("%d,%d,%d,%s,%s,%s\n",
                                l.getId(), l.getBookId(), l.getMemberId(), l.getLoanDate(), l.getDateDue(), l.getReturnDate()));
                    }
                }
                JOptionPaneHelper.info("Préstamos exportados correctamente a CSV.");
            }
        } catch (Exception e) {
            JOptionPaneHelper.error("Error al exportar préstamos: " + e.getMessage());
        }
    }

    // ================== EXPORTACIONES ==================
    private void exportMenu() throws IOException {
        Object[] options = {"1. Exportar Catálogo a CSV", "2. Volver"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Seleccione el tipo de exportación:",
            "Exportaciones",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            exportarLibrosACSV();
        }
    }

    public void exportarLibrosACSV() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Inventario de Libros como CSV");
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();

            if (!ruta.toLowerCase().endsWith(".csv")) {
                ruta += ".csv";
            }

            try {
                List<Book> books = this.bookService.findAll();

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
                    bw.write("ID,ISBN,Title,Author,Category,TotalCopies,AvailableCopies,ReferencePrice");
                    bw.newLine();

                    for (Book book : books) {
                        String line = String.format("%d,%s,\"%s\",\"%s\",%s,%d,%d,%.2f",
                                book.getId(),
                                book.getIsbn(),
                                book.getTitle().replace("\"", "\"\""),
                                book.getAuthor().replace("\"", "\"\""),
                                book.getCategory(),
                                book.getTotalCopies(),
                                book.getAvailableCopies(),
                                book.getReferencePrice()
                        );
                        bw.write(line);
                        bw.newLine();
                    }

                    JOptionPaneHelper.info("Libros exportados a CSV correctamente:\n" + ruta);
                }
            } catch (BusinessException e) {
                JOptionPaneHelper.error("Error al listar libros para exportar: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Exportación cancelada.");
        }
    }

    // ================== USUARIOS ==================
    private void userMenu() {
        Object[] options = {"1. Gestión de Roles", "2. Gestión de Permisos", "3. Volver"};
        JOptionPane.showOptionDialog(
            null,
            "Funcionalidad de gestión de Usuarios.",
            "Gestión de Usuarios",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
    }
}