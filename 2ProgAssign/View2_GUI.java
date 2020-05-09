/********************************************************
 * Swing GUI for handling updating the CHARACTR table   *
 * in a database                                        *
 * @author Justin Weigle                                *
 * edited: 09 May 2020                                  *
 ********************************************************/
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

        /** Initialize all the parts of the GUI */
        initCharList();
        initUpdateBtn();
        initCharTable();

        window.pack();
        window.setVisible(true);
    }

    /**
     * Initializes the character list for the current player
     * (at this time just player with id 0)
     */
    private void initCharList () throws SQLException
    {
        /** Get the characters from the DB that are controlled by player 0 */
        String[] charListData = helper.getDBCharList(0);

        /** Populate the list model with the character names */
        DefaultListModel<Object> charListModel = new DefaultListModel<Object>();
        for (int i = 0; i < charListData.length; i++) {
            charListModel.addElement(charListData[i]);
        }

        /** Create list and set it to single selection mode */
        characterList = new JList<Object>(charListModel);
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /** Make a scroll pane for the list to go in */
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(characterList);

        /** Make a MouseListener to act on double clicks */
        characterList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selection = (String) characterList.getSelectedValue();
                    try {
                        /* get the character info */
                        charInfo = helper.getCharInfo(selection);
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                    }
                    /* show the selected character's info in the table */
                    updateCharTable(charInfo);
                }
            }
        });

        window.add(scrollPane, BorderLayout.NORTH);
    }

    /**
     * Initializes the update button which opens a dialog to confirm
     * an update to the database
     */
    private void initUpdateBtn () throws SQLException
    {
        /** Make a padded panel to put the button in */
        JPanel btnPanel = new JPanel();
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /** Make the button and add it to the panel */
        updateBtn = new JButton("Update");
        btnPanel.add(updateBtn);

        /** Add a listener to the button that opens a confirmation dialog */
        updateBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    confirmDBUpdate();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
        });

        window.add(btnPanel, BorderLayout.WEST);
    }

    /**
     * Creates a dialog box that allows confirmation of pushing an update
     * to the database
     */
    private void confirmDBUpdate () throws SQLException
    {
        /** Open dialog box with the following contents */
        int n = JOptionPane.showConfirmDialog(
            window,
            "Are you sure you want to update the database?",
            "Confirm choice",
            JOptionPane.YES_NO_OPTION);

        /** Update the database when the Yes button is clicked */
        if (n == 0) {
            helper.updateCharAttrs(columnNames, charInfo);
        } else if (n == 1) {
            System.out.println("Update cancelled");
        }
    }

    /**
     * Updates the character table with the given data
     */
    private void updateCharTable (Object[] data)
    {
        /** Get the current contents of the character table */
        DefaultTableModel model = (DefaultTableModel)charTable.getModel();

        /** Remove the single row kept in the character table */
        if (model.getRowCount() > 0) {
            model.removeRow(0);
        }

        /** Add the given data to the table */
        model.addRow(data);

        /** Refresh the table */
        charTable.repaint();
    }

    /**
     * Initializes the character table to hold info for a selected character
     */
    private void initCharTable ()
    {
        Object[][] data = {};

        /** Add a blank data set to the table to initialize it */
        charTable = new JTable(new DefaultTableModel(data, columnNames));
        /** Set the width for all the columns */
        for (int i = 0; i < charTable.getColumnModel().getColumnCount(); i++) {
            charTable.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        /** Make a padded panel to store the table in */
        JPanel tblPanel = new JPanel();
        tblPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        tblPanel.setLayout(new GridLayout(0, 1));

        /** Add the table and its header to the panel */
        tblPanel.add(charTable.getTableHeader(), BorderLayout.NORTH);
        tblPanel.add(charTable, BorderLayout.CENTER);

        /** Set a listener for the table to update the character info
         * array when it's updated in the table */
        charTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    for (int i = 0; i < charTable.getModel().getColumnCount(); i++) {
                        charInfo[i] = (Object) charTable.getModel().getValueAt(0, i);
                    }
                }
            }
        });

        window.add(tblPanel, BorderLayout.SOUTH);
    }
}
