import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class View2_GUI
{
    private Helper_JDBC helper = null;
    private JFrame window;
    private JList<Object> characterList;
    private JButton updateBtn;
    private JTable charTable;
    private Object[] charInfo = null;

    private String[] columnNames = {
        "name",
        "strength",
        "stamina",
        "curHP",
        "maxHP",
        "Loc_id"
    };

    View2_GUI(Helper_JDBC helper) throws SQLException
    {
        this.helper = helper;
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            initCharList();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
//        try {
            initUpdateBtn();
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//        }
//        try {
            initCharTable();
//        } catch (SQLException sqle) {
//            sqle.printStackTrace();
//        }

        Object[] data = {
            "Adam", 95, 20, 92, 100, 0
        };

        updateCharTable(data);

        window.pack();
        window.setVisible(true);
    }

    private void initCharList () throws SQLException
    {
        String[] leData = helper.getDBCharList(0);

        DefaultListModel<Object> charListModel = new DefaultListModel<Object>();
        for (int i = 0; i < leData.length; i++) {
            charListModel.addElement(leData[i]);
        }

        characterList = new JList<Object>(charListModel);
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(characterList);

        characterList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // TODO handle return from getSelectedValue
                    // to run a select on the database
                    String selection = (String) characterList.getSelectedValue();
                    try {
                        charInfo = helper.getCharInfo(selection);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                    }
                    updateCharTable(charInfo);
                }
            }
        });


        window.add(scrollPane, BorderLayout.NORTH);
    }

    private void initUpdateBtn () throws SQLException
    {
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        updateBtn = new JButton("Update");
        btnPanel.add(updateBtn);

        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    confirmDBUpdate();
                } catch (SQLException sqle) {
                }
            }
        });

        window.add(btnPanel, BorderLayout.WEST);
    }

    private void confirmDBUpdate () throws SQLException
    {
        int n = JOptionPane.showConfirmDialog(
            window,
            "Are you sure you want to update the database?",
            "Confirm choice",
            JOptionPane.YES_NO_OPTION);

        if (n == 0) {
            helper.updateCharAttrs(columnNames, charInfo);
        } else if (n == 1) {
            System.out.println("Update cancelled");
        }
    }

    private void updateCharTable (Object[] data)
    {
        DefaultTableModel model = (DefaultTableModel)charTable.getModel();

        if (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        model.addRow(data);

        charTable.repaint();
    }

    private void initCharTable ()
    {
        Object[][] data = {};

        charTable = new JTable(new DefaultTableModel(data, columnNames));
        for (int i = 0; i < charTable.getColumnModel().getColumnCount(); i++) {
            charTable.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        JPanel tblPanel = new JPanel();
        tblPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        tblPanel.setLayout(new GridLayout(0, 1));

        tblPanel.add(charTable.getTableHeader(), BorderLayout.NORTH);
        tblPanel.add(charTable, BorderLayout.CENTER);

        charTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    for (int i = 0; i < charTable.getModel().getColumnCount(); i++) {
                        // TODO storing updated info instead of just printing it
//                        System.out.println(charTable.getModel().getValueAt(0, i));
                        charInfo[i] = (Object) charTable.getModel().getValueAt(0, i);
                    }
                }
            }
        });

        window.add(tblPanel, BorderLayout.SOUTH);
    }

//    public static void main(String args[])
//    {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new View2_GUI();
//            }
//        });
//    }

}
