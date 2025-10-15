
package com.codeup.libronova.UI;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author yoshi
 */
public class TableFormater {
    
  
    public static void showTable(String title, String[] columns, List<Object[]> data) {
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Object[] row : data) {
            model.addRow(row);
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
    }
}
    

